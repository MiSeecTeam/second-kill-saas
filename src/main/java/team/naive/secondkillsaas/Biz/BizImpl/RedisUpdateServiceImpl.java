package team.naive.secondkillsaas.Biz.BizImpl;

import org.springframework.beans.factory.annotation.Autowired;
import team.naive.secondkillsaas.Biz.RedisUpdateService;
import team.naive.secondkillsaas.DO.SkuQuantityDO;
import team.naive.secondkillsaas.Mapper.ItemDetailMapper;
import team.naive.secondkillsaas.Mapper.RedisMapper;
import team.naive.secondkillsaas.Mapper.SkuDetailMapper;
import team.naive.secondkillsaas.Mapper.SkuQuantityMapper;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/19
 * @description:
 */
public class RedisUpdateServiceImpl implements RedisUpdateService {

    @Autowired
    private RedisMapper redisMapper;

    @Autowired
    private ItemDetailMapper itemDetailMapper;

    @Autowired
    private SkuDetailMapper skuDetailMapper;

    @Autowired
    private SkuQuantityMapper skuQuantityMapper;

    @Override
    public int readAllItemDetailToRedis() {
        //TODO
        return 0;
    }

    @Override
    public int readAllSkuDetailToRedis() {
        //TODO
        return 0;
    }

    @Override
    public int readAllSkuQuantityToRedis() {
        //TODO
        return 0;
    }

    @Override
    public void enableKillingSku(long skuId) {
        SkuQuantityDO skuQuantityDO = skuQuantityMapper.selectByPrimaryKey(skuId);
        redisMapper.saveKillSkuQuantity(skuQuantityDO);
    }
}
