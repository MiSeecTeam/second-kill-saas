package team.naive.secondkillsaas.Redis;

import org.apache.ibatis.annotations.Mapper;
import team.naive.secondkillsaas.DO.ItemDetailDO;
import team.naive.secondkillsaas.DO.SkuDetailDO;
import team.naive.secondkillsaas.DO.SkuQuantityDO;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/19
 * @description:
 */
public interface RedisMapper {

    /*
    以下是把mysql数据库中的三种数据缓存到redis中的6个方法
     */
    ItemDetailDO getItemDetail(long itemId);
    void saveItemDetail(ItemDetailDO itemDetailDO);

    SkuDetailDO getSkuDetail(long skuId);
    void saveSkuDetail(SkuDetailDO skuDetailDO);

    SkuQuantityDO getSkuQuantity(long skuId);
    void saveSkuQuantity(SkuQuantityDO skuQuantityDO);

    /*
    这两个方法是秒杀时，扣减库存专用的
     */
    SkuQuantityDO getKillSkuQuantity(long skuId);
    void saveKillSkuQuantity(SkuQuantityDO skuQuantityDO);
}
