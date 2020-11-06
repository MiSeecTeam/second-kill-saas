package team.naive.secondkillsaas.Biz.BizImpl;/**
 * Created by Administrator on 2019/6/17.
 */

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import team.naive.secondkillsaas.BO.SkuKillBO;
import team.naive.secondkillsaas.Biz.ItemService;
import team.naive.secondkillsaas.Biz.KillService;
import team.naive.secondkillsaas.Biz.OrderServiceForBiz;
import team.naive.secondkillsaas.DO.SkuQuantityDO;
import team.naive.secondkillsaas.DTO.KillDTO;
import team.naive.secondkillsaas.DTO.OrderFormDTO;
import team.naive.secondkillsaas.Redis.RedisService;
import team.naive.secondkillsaas.Mapper.SkuQuantityMapper;
import team.naive.secondkillsaas.Security.UserValidation;
import team.naive.secondkillsaas.Utils.RedisUtils;
import team.naive.secondkillsaas.VO.ResponseVO;

import java.util.Date;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */
@Service
public class KillServiceImpl implements KillService {

    private static final String KILL_LOCK_PREFIX = "KILL_LOCK_PREFIX";
    private static final String KILL_IDEMPOTENT_PREFIX = "KILL_IDEMPOTENT_PREFIX";

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private SkuQuantityMapper skuQuantityMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderServiceForBiz orderServiceForBiz;

    // todo: 日志
    private static final Logger log= LoggerFactory.getLogger(KillServiceImpl.class);

    @Override
    public ResponseVO killItem(@RequestBody KillDTO killDTO) throws Exception {
        ResponseVO responseVO = new ResponseVO();
        Long userId = killDTO.getUserId(), skuId = killDTO.getSkuId();
        Long transactionId = killDTO.getTransactionId();

        // todo: 安全系统应该另外部署，此处应为一个远程调用
        if (!userValidation.isKillValid(userId)){
            responseVO.setSuccess(false);
            responseVO.setMessage("用户验证不通过");
            return responseVO;
        }

        /*
        执行业务逻辑
        */
        //从redis中取出SkuKillBO对象
        SkuKillBO skuKillBO = redisService.getSkuKillBO(skuId);
        Date start_time = skuKillBO.getStartTime();
        Date end_time = skuKillBO.getEndTime();
        Date arrival = new Date();

        /*
         * 情况1：秒杀已结束
         * 不计入幂等
         */
        if(arrival.after(end_time)){
            System.out.println("顾客"+userId+"抢购商品"+skuId+"，失败。该商品已经结束秒杀。");
            responseVO.setSuccess(false);
            responseVO.setMessage("秒杀已经结束。");
            return responseVO;
        }

        /*
         * 情况2：秒杀未开始
         * 不计入幂等
         */
        if(arrival.before(start_time)){
            System.out.println("顾客"+userId+"抢购商品"+skuId+"，失败。该商品还未开始秒杀。");
            responseVO.setSuccess(false);
            responseVO.setMessage("秒杀尚未开始。");
            return responseVO;
        }

        /*
         * 情况3：正在进行秒杀
         * 计入幂等
         */

        //先进行幂等判断，如果是正在进行抢购，则轮询等待结果，如果是已经有结果，返回该结果。
        while (true){
            if(tryLock("_"+skuId+"_"+userId)){
                ResponseVO lastResult = tryGetLast(transactionId);
                //如果不为空，说明已经抢购过了
                if (lastResult != null) {
                    //下面不会修改lastResult，解锁
                    unLock("_"+skuId+"_"+userId);
                    //如果是killing，说明有其他进程正在执行抢购
                    //等待得出结果，然后返回结果
                    while(lastResult.getMessage().equals("killing")){
                        lastResult = tryGetLast(transactionId);
                    }
                    if (!lastResult.getMessage().equals("Order failure.")) {
                        return lastResult;
                    }
                }
                lastResult = new ResponseVO();
                lastResult.setSuccess(true);
                lastResult.setContent(false);
                lastResult.setMessage("killing");
                saveLast(transactionId, lastResult);
                //然后放行执行抢购
                unLock("_"+skuId+"_"+userId);
                break;
            }
        }

        /*
        执行分桶抢购流程
         */
        //是否抢购到了商品
        boolean killed = false;
        // 是否出现创建订单失败
        boolean orderFailure = false;
        //已经进入过的桶的个数
        int entered = 0;
        //算出第一个要访问的桶
        int index = (int) (transactionId % skuKillBO.getBucketAmount());
        while (!killed){
            System.out.println("顾客"+userId+"正在尝试抢购商品"+skuId);
            // 已经遍历所有桶，但是没有抢到
            if(entered==skuKillBO.getBucketAmount()){
                // 更新总库存
                if (!orderFailure) {
                    SkuQuantityDO read = redisService.getSkuQuantity(skuId);
                    read.setAmount(0L);
                    redisService.saveSkuQuantity(read);
                    System.out.println("顾客"+userId+"抢购商品"+skuId+"，失败。已经抢完。");
                    responseVO.setSuccess(false);
                    responseVO.setMessage("该商品已经抢完了");
                }
                // 出现订单错误，可以重试
                else {
                    System.out.println("顾客"+userId+"抢购商品"+skuId+"，失败。订单创建失败。");
                    responseVO.setSuccess(false);
                    responseVO.setMessage("Order failure.");
                }
                break;
            }
            //循环尝试锁桶，进入if之后一定要break出来
            while (true){
                //尝试进入桶index，进入之后，运算完成要unlock桶
                if(tryLock("_BUCKET_"+skuId+"_"+index)){
                    //在桶内进行秒杀
                    long amount = redisService.getSkuBucketContent(skuId, index);
                    if(amount>0){
                        /**
                         * 创建订单
                         */
                        OrderFormDTO orderFormDTO = new OrderFormDTO();
                        orderFormDTO.setSkuId(skuId);
                        orderFormDTO.setUserId(userId);
                        ResponseVO orderResponse = orderServiceForBiz.createOrder(orderFormDTO);
                        if (orderResponse.getSuccess()) {
                            amount--;
                            //修改桶中库存
                            redisService.saveSkuBucketContent(skuId, index, amount);
                            System.out.println("顾客"+userId+"抢购商品"+skuId+"，成功。");
                            /*
                             * 更新用于读的skuQuantity。注意，这没有上锁，所以用于读的数量是不准的。
                             */
                            SkuQuantityDO read = redisService.getSkuQuantity(skuId);
                            Long totalAmount = read.getAmount();
                            read.setAmount(--totalAmount);
                            redisService.saveSkuQuantity(read);

                            responseVO.setSuccess(true);
                            responseVO.setMessage("抢购成功！！！");
                            responseVO.setContent(orderResponse.getContent());
                            killed = true;
                            orderFailure = false;
                        } else {
                            responseVO.setSuccess(false);
                            responseVO.setMessage(orderResponse.getMessage());
                            orderFailure = true;
                        }
                    }
                    else{
                        //这个桶已经没有库存了，转下一个桶
                        index++;
                        entered++;
                        if(index==skuKillBO.getBucketAmount()-1){
                            index = 0;
                        }
                    }
                    // 退出桶遍历
                    // 出桶前解锁
                    unLock("_BUCKET_"+skuId+"_"+index);
                    break;
                }
            }
        }

        saveLast(transactionId, responseVO);

        return responseVO;
    }

    private boolean tryLock(String item)  {
        return redisUtils.setIfAbsent(KILL_LOCK_PREFIX + item);
    }

    private void unLock(String item)  {
        redisUtils.del(KILL_LOCK_PREFIX + item);
    }

    // 获得上一次结果
    private ResponseVO tryGetLast(Long transactionId)  {
        String key = KILL_IDEMPOTENT_PREFIX + transactionId;
        Object res = redisUtils.get(key);
        if (res != null) {
            return (ResponseVO) res;
        }
        return null;
    }

    private void saveLast(Long transactionId, ResponseVO last)  {
        String key = KILL_IDEMPOTENT_PREFIX + transactionId;
        redisUtils.set(key, last);
    }



}








































