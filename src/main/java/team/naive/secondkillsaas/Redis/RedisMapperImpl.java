package team.naive.secondkillsaas.Redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.naive.secondkillsaas.DO.ItemDetailDO;
import team.naive.secondkillsaas.DO.SkuDetailDO;
import team.naive.secondkillsaas.DO.SkuQuantityDO;
import team.naive.secondkillsaas.Utils.RedisUtils;

import java.util.Map;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/19
 * @description:
 */
@Component
public class RedisMapperImpl implements RedisMapper {
    public static final String ITEM_DETAIL_HASH = "ITEM_DETAIL_HASH";
    public static final String SKU_DETAIL_HASH = "SKU_DETAIL_HASH";
    public static final String SKU_QUANTITY_HASH = "SKU_QUANTITY_HASH";
    public static final String KILL_SKU_QUANTITY_PREFIX = "KILL_SKU_QUANTITY_PREFIX";

    @Autowired
    private RedisUtils redisUtils;

    /*
    在redis中保存ItemDetail
     */
    @Override
    public ItemDetailDO getItemDetail(long itemId){
        return JSONObject.parseObject(
                JSONObject.toJSONString(redisUtils.hget(SKU_DETAIL_HASH, String.valueOf(itemId))),
                ItemDetailDO.class);
    }

    @Override
    public void saveItemDetail(ItemDetailDO itemDetailDO){
        redisUtils.hset(ITEM_DETAIL_HASH,String.valueOf(itemDetailDO.getItemId()), itemDetailDO);
    }

    /*
    在redis中保存SkuDetail
     */
    @Override
    public SkuDetailDO getSkuDetail(long skuId){
        return JSONObject.parseObject(
                JSONObject.toJSONString(redisUtils.hget(SKU_DETAIL_HASH, String.valueOf(skuId))),
                SkuDetailDO.class);
    }

    @Override
    public void saveSkuDetail(SkuDetailDO skuDetailDO){
        redisUtils.hset(SKU_DETAIL_HASH,String.valueOf(skuDetailDO.getSkuId()), skuDetailDO);
    }

    /*
    在redis中保存SkuQuantity
     */

    @Override
    public SkuQuantityDO getSkuQuantity(long skuId){
        return JSONObject.parseObject(
                JSONObject.toJSONString(redisUtils.hget(SKU_QUANTITY_HASH, String.valueOf(skuId))),
                SkuQuantityDO.class);
    }

    @Override
    public void saveSkuQuantity(SkuQuantityDO skuQuantityDO){
        redisUtils.hset(SKU_QUANTITY_HASH,String.valueOf(skuQuantityDO.getSkuId()), skuQuantityDO);
    }

    /*
    抢购的时候，读和写sku数量
     */

    @Override
    public SkuQuantityDO getKillSkuQuantity(long skuId){
        return JSONObject.parseObject(
                JSONObject.toJSONString(redisUtils.hmget(KILL_SKU_QUANTITY_PREFIX+skuId)),
                SkuQuantityDO.class);
    }

    @Override
    public void saveKillSkuQuantity(SkuQuantityDO skuQuantityDO){
        long skuId = skuQuantityDO.getSkuId();
        Map<Object, Object> newQuantity = JSONObject.parseObject(
                JSONObject.toJSONString(skuQuantityDO), Map.class);
        redisUtils.hmset(KILL_SKU_QUANTITY_PREFIX+skuId, newQuantity);
    }
}
