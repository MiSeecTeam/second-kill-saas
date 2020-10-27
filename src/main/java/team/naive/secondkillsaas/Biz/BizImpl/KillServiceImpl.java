package team.naive.secondkillsaas.Biz.BizImpl;/**
 * Created by Administrator on 2019/6/17.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import team.naive.secondkillsaas.Biz.ItemService;
import team.naive.secondkillsaas.Biz.KillService;
import team.naive.secondkillsaas.DO.SkuQuantityDO;
import team.naive.secondkillsaas.DTO.KillDTO;
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

        responseVO.setContent(false);
        responseVO.setSuccess(true);
        // 目前为单系统所以简单上锁，todo：应对多系统问题
        // 尝试获取分布式锁，todo: 组件化封装+超时重试
        while(true){
            System.out.println("顾客"+userId+"正在尝试抢购商品"+skuId);
            if (tryLock(skuId)) {
                // 幂等控制，todo：同样的，消除性能瓶颈+组件化
                ResponseVO lastResult = tryGetLast(transactionId);
                if (lastResult != null) {
                    return lastResult;
                }

                /*
                执行业务逻辑
                 */
                //从redis中取出SkuQuantityDO对象
                SkuQuantityDO skuQuantityDO = redisService.getKillSkuQuantity(skuId);
                Date start_time = skuQuantityDO.getStartTime();
                Date end_time = skuQuantityDO.getEndTime();
                Date arrival = new Date();

                /**
                 * 情况1：秒杀已结束
                 * 不计入幂等
                 */
                if(arrival.after(end_time)){
                    System.out.println("顾客"+userId+"抢购商品"+skuId+"，失败。该商品已经结束秒杀。");
                    responseVO.setMessage("秒杀已经结束。");
                    return responseVO;
                }

                /**
                 * 情况2：秒杀未开始
                 * 不计入幂等
                 */
                if(arrival.after(start_time)){
                    System.out.println("顾客"+userId+"抢购商品"+skuId+"，失败。该商品还未开始秒杀。");
                    responseVO.setMessage("秒杀尚未开始。");
                    return responseVO;
                }

                /**
                 * 情况3：正在进行秒杀
                 * 计入幂等
                 */
                long amount = skuQuantityDO.getAmount();
                //如果有库存的话，成功抢购。没库存的话，抢购失败。
                if(amount>0){
                    amount--;
                    //放回redis中去
                    skuQuantityDO.setAmount(amount);
                    skuQuantityDO.setGmtModified(new Date());
                    redisService.saveKillSkuQuantity(skuQuantityDO);
                    System.out.println("顾客"+userId+"抢购商品"+skuId+"，成功。");
                    responseVO.setContent(true);
                    /**
                     * 更新 用于读的skuQuantity
                     */
                    SkuQuantityDO read = redisService.getSkuQuantity(skuId);
                    read.setAmount(amount);
                    redisService.saveSkuQuantity(read);
                }
                else{//库存已经没了，抢购失败
                    System.out.println("顾客"+userId+"抢购商品"+skuId+"，失败。已经抢完。");
                    responseVO.setContent(false);
                    responseVO.setMessage("该商品已经抢完了");
                }

                // 保存执行记录，方便幂等控制
                saveLast(transactionId, responseVO);

                // 分布式锁解锁
                unLock(skuId);

                break;
            }
        }
        return responseVO;
    }

    private boolean tryLock(Long skuId)  {
        return redisUtils.setIfAbsent(KILL_LOCK_PREFIX + skuId);
    }

    private void unLock(Long skuId)  {
        redisUtils.del(KILL_LOCK_PREFIX + skuId);
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








































