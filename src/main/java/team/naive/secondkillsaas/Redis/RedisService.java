package team.naive.secondkillsaas.Redis;

import org.apache.ibatis.annotations.Mapper;
import team.naive.secondkillsaas.BO.SkuKillBO;
import team.naive.secondkillsaas.DO.ItemDetailDO;
import team.naive.secondkillsaas.DO.SkuDetailDO;
import team.naive.secondkillsaas.DO.SkuQuantityDO;

import java.util.List;
import java.util.Map;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/19
 * @description:
 */
public interface RedisService {

    /*
    以下是把mysql数据库中的三种数据缓存到redis中的6个方法
     */
    ItemDetailDO getItemDetail(long itemId);
    void saveItemDetail(ItemDetailDO itemDetailDO);
    Map<Object, Object> getAllItemDetail();

    SkuDetailDO getSkuDetail(long skuId);
    void saveSkuDetail(SkuDetailDO skuDetailDO);

    List<SkuDetailDO> getSkuDetailListByItemId(long itemId);

    SkuQuantityDO getSkuQuantity(long skuId);
    void saveSkuQuantity(SkuQuantityDO skuQuantityDO);

    /*
    这两个方法是秒杀时，获取秒杀结果体信息
     */
    SkuKillBO getSkuKillBO(long skuId);
    void saveSkuKillBO(SkuQuantityDO skuQuantityDO);

    /*
    读写用于秒杀的桶
     */
    long getSkuBucketContent(long skuId, int bucketIndex);
    void saveSkuBucketContent(long skuId, int bucketIndex, long amount);
}
