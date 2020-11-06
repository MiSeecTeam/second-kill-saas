package team.naive.secondkillsaas.Redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.naive.secondkillsaas.BO.SkuKillBO;
import team.naive.secondkillsaas.DO.ItemDetailDO;
import team.naive.secondkillsaas.DO.SkuDetailDO;
import team.naive.secondkillsaas.DO.SkuQuantityDO;
import team.naive.secondkillsaas.Utils.RedisUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/19
 * @description:
 */
@Component
public class RedisServiceImpl implements RedisService {
    public static final String ITEM_DETAIL_HASH = "ITEM_DETAIL_HASH";
    public static final String SKU_DETAIL_HASH = "SKU_DETAIL_HASH";
    public static final String SKU_DETAIL_HASH_GATHER_BY_ITEM_ID = "SKU_DETAIL_HASH_GATHER_BY_ITEM_ID";
    public static final String SKU_QUANTITY_HASH = "SKU_QUANTITY_HASH";
    public static final String SKU_KILL_PREFIX = "SKU_KILL_PREFIX";
    public static final String SKU_BUCKET_HASH = "SKU_BUCKET_HASH";

    @Autowired
    private RedisUtils redisUtils;

    /*
    在redis中保存ItemDetail
     */
    @Override
    public ItemDetailDO getItemDetail(long itemId){
        return JSONObject.parseObject(
                JSONObject.toJSONString(redisUtils.hget(ITEM_DETAIL_HASH, String.valueOf(itemId))),
                ItemDetailDO.class);
    }

    @Override
    public void saveItemDetail(ItemDetailDO itemDetailDO){
        redisUtils.hset(ITEM_DETAIL_HASH,String.valueOf(itemDetailDO.getItemId()), itemDetailDO);
    }

    /*
    获得全部itemDetail
     */
    @Override
    public Map<Object, Object> getAllItemDetail(){
        return redisUtils.hmget(ITEM_DETAIL_HASH);
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
        redisUtils.hset(SKU_DETAIL_HASH_GATHER_BY_ITEM_ID+"_"+skuDetailDO.getItemId(),
                String.valueOf(skuDetailDO.getSkuId()), skuDetailDO);
        redisUtils.hset(SKU_DETAIL_HASH, String.valueOf(skuDetailDO.getSkuId()), skuDetailDO);
    }

    @Override
    public List<SkuDetailDO> getSkuDetailListByItemId(long itemId) {
        List<SkuDetailDO> resultList = new ArrayList<>();
        Map<Object, Object> skuDetailMap = redisUtils.hmget(SKU_DETAIL_HASH_GATHER_BY_ITEM_ID + "_" + itemId);
        for(Object object: skuDetailMap.values()){
            resultList.add((SkuDetailDO)object);
        }
        return resultList;
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
    抢购的时候，读和写sku秒杀结构体
     */

    @Override
    public SkuKillBO getSkuKillBO(long skuId){
        return JSONObject.parseObject(
                JSONObject.toJSONString(redisUtils.hmget(SKU_KILL_PREFIX+"_"+skuId)),
                SkuKillBO.class);
    }

    @Override
    public void saveSkuKillBO(SkuQuantityDO skuQuantityDO){
        long skuId = skuQuantityDO.getSkuId();
        SkuKillBO skuKillBO = new SkuKillBO(skuQuantityDO);
        Map<Object, Object> tempMap = JSONObject.parseObject(
                JSONObject.toJSONString(skuKillBO), Map.class);
        redisUtils.hmset(SKU_KILL_PREFIX+"_"+skuId, tempMap);
    }

    @Override
    public long getSkuBucketContent(long skuId, int bucketIndex){
        String number = String.valueOf(redisUtils.hget(SKU_BUCKET_HASH, skuId+"_"+bucketIndex));
        return Long.parseLong(number) ;
    }

    @Override
    public void saveSkuBucketContent(long skuId, int bucketIndex, long amount){
        redisUtils.hset(SKU_BUCKET_HASH, skuId+"_"+bucketIndex, amount);
    }
}
