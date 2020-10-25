package team.naive.secondkillsaas.Biz.BizImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.naive.secondkillsaas.Biz.RedisUpdateService;
import team.naive.secondkillsaas.DO.*;
import team.naive.secondkillsaas.Mapper.ItemDetailMapper;
import team.naive.secondkillsaas.Redis.RedisService;
import team.naive.secondkillsaas.Mapper.SkuDetailMapper;
import team.naive.secondkillsaas.Mapper.SkuQuantityMapper;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/19
 * @description:
 */
@Service
public class RedisUpdateServiceImpl implements RedisUpdateService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ItemDetailMapper itemDetailMapper;

    @Autowired
    private SkuDetailMapper skuDetailMapper;

    @Autowired
    private SkuQuantityMapper skuQuantityMapper;

    @Override
    public int readAllItemDetailToRedis() {
        System.out.println("Updating ItemDetail...");

        //获得全部的ItemDetailDO
        ItemDetailDOExample example = new ItemDetailDOExample();
//        example.createCriteria().andIsDeletedEqualTo(false);
        List<ItemDetailDO> itemDetailDOS = itemDetailMapper.selectByExample(example);
        for(ItemDetailDO itemDetailDO: itemDetailDOS){
            redisService.saveItemDetail(itemDetailDO);
        }

        System.out.println("Update ItemDetail total: "+itemDetailDOS.size());
        return itemDetailDOS.size();
    }

    @Override
    public int readAllSkuDetailToRedis() {
        System.out.println("Updating SkuDetail...");

        SkuDetailDOExample example = new SkuDetailDOExample();
//        example.createCriteria().andIsDeletedEqualTo(false);
        List<SkuDetailDO> skuDetailDOS = skuDetailMapper.selectByExample(example);
        for(SkuDetailDO skuDetailDO: skuDetailDOS){
            redisService.saveSkuDetail(skuDetailDO);
        }
        System.out.println("Update SkuDetail total: "+skuDetailDOS.size());
        return skuDetailDOS.size();
    }

    @Override
    public int readAllSkuQuantityToRedis() {
        System.out.println("Updating SkuQuantity...");

        SkuQuantityDOExample example = new SkuQuantityDOExample();
//        example.createCriteria().andIsDeletedEqualTo(false);
        List<SkuQuantityDO> skuQuantityDOS = skuQuantityMapper.selectByExample(example);
        for(SkuQuantityDO skuQuantityDO:skuQuantityDOS){
            //缓存用于读取的SkuQuantity
            redisService.saveSkuQuantity(skuQuantityDO);
            //缓存用于杀价的SkuQuantity
            redisService.saveKillSkuQuantity(skuQuantityDO);
        }
        System.out.println("Update SkuQuantity total: "+skuQuantityDOS.size());

        return skuQuantityDOS.size();
    }

    @Override
    public void enableKillingSku(long skuId) {
        SkuQuantityDO skuQuantityDO = skuQuantityMapper.selectByPrimaryKey(skuId);
        redisService.saveKillSkuQuantity(skuQuantityDO);
    }
}
