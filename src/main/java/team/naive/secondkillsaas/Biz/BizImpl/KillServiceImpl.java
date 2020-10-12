package team.naive.secondkillsaas.Biz.BizImpl;/**
 * Created by Administrator on 2019/6/17.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import team.naive.secondkillsaas.BO.SkuQuantityBO;
import team.naive.secondkillsaas.Biz.ItemService;
import team.naive.secondkillsaas.Biz.KillService;
import team.naive.secondkillsaas.DO.SkuQuantityDO;
import team.naive.secondkillsaas.DTO.KillDTO;
import team.naive.secondkillsaas.Mapper.SkuQuantityMapper;
import team.naive.secondkillsaas.Security.UserValidation;
import team.naive.secondkillsaas.Utils.RedisUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */
@Service
public class KillServiceImpl implements KillService {

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private SkuQuantityMapper skuQuantityMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RedisUtils redisUtils;

    // todo: 日志
    private static final Logger log= LoggerFactory.getLogger(KillServiceImpl.class);

    @Override
    public Boolean killItem(@RequestBody KillDTO killDTO) throws Exception {
        Long userId = killDTO.getUserId(), skuId = killDTO.getSkuId();
        Long transactionId = killDTO.getTransactionId();

        // todo: 安全系统应该另外部署，此处应为一个远程调用
        if (!userValidation.isKillValid(userId)){
            return false;
        }

        Boolean result = false;
        // 目前为单系统所以简单上锁，todo：应对多系统问题
        // 尝试获取分布式锁，todo: 组件化封装+超时重试
        if (tryLock(skuId)) {
            // 幂等控制，todo：同样的，消除性能瓶颈+组件化
            Boolean lastResult = tryGetLast(transactionId);
            if (lastResult != null) {
                return lastResult;
            }

            // 执行业务逻辑
            SkuQuantityDO skuQuantityDO = skuQuantityMapper.selectByPrimaryKey(skuId);
            if (skuQuantityDO.getAmount()>0) {
                skuQuantityDO.setAmount(skuQuantityDO.getAmount()-1);
                skuQuantityDO.setGmtModified(new Date());
                skuQuantityMapper.updateByPrimaryKey(skuQuantityDO);
                result = true;
            }

            // 保存执行记录，方便幂等控制
            saveLast(transactionId, result);

            // 分布式锁解锁
            unLock(skuId);
        } else {
            // todo: 自定义Exception
            throw new Exception();
        }
        return result;
    }

    private static final String KILL_LOCK_PREFIX = "KILL_LOCK_PREFIX";

    private boolean tryLock(Long skuId)  {
        if (redisUtils.get(KILL_LOCK_PREFIX + skuId)!=null) {
            return false;
        }
        redisUtils.set(KILL_LOCK_PREFIX + skuId);
        return true;
    }

    private void unLock(Long skuId)  {
        redisUtils.del(KILL_LOCK_PREFIX + skuId);
    }

    private static final String KILL_IDEMPOTENT_PREFIX = "KILL_IDEMPOTENT_PREFIX";

    // 虽然这里是Boolean，但其实最好是一个对象
    private Boolean tryGetLast(Long transactionId)  {
        String key = KILL_IDEMPOTENT_PREFIX + transactionId;
        Object res = redisUtils.get(key);
        if (res != null) {
            return (Boolean) res;
        }
        return null;
    }

    private void saveLast(Long transactionId, Boolean last)  {
        String key = KILL_IDEMPOTENT_PREFIX + transactionId;
        redisUtils.set(key, last);
    }



}








































