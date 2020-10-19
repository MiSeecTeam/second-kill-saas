package team.naive.secondkillsaas.Biz;

import org.springframework.stereotype.Service;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/19
 * @description: 此服务专门负责将mysql数据库中的信息更新到redis。
 * 1、价格和库存信息，把数据库中的这些信息全部缓存到redis中，加快前端访问速度。都是只读的。
 *
 * 2、面向抢购的库存信息，该库存会频繁修改。
 */
@Service
public interface RedisUpdateService {

    /**
     * 把数据库中的ItemDetail缓存到redis中
     * @return 一共多少条
     */
    int readAllItemDetailToRedis();
    int readAllSkuDetailToRedis();
    int readAllSkuQuantityToRedis();

    /*
    把某商品的剩余库存放到redis里去
     */
    void enableKillingSku(long skuId);
}
