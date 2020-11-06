package team.naive.secondkillsaas;

import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import team.naive.secondkillsaas.DO.ItemDetailDO;
import team.naive.secondkillsaas.DO.SkuDetailDO;
import team.naive.secondkillsaas.DO.SkuQuantityDO;
import team.naive.secondkillsaas.Mapper.ItemDetailMapper;
import team.naive.secondkillsaas.Redis.RedisService;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/19
 * @description:
 */
public class RedisTest extends SecondKillSaasApplicationTests {

    @Autowired
    RedisService redisService;
    @Autowired
    ItemDetailMapper itemDetailMapper;


    //向redis填一条数据
    @Ignore
    @Test
    void test1(){
        ItemDetailDO itemDetailDO = itemDetailMapper.selectByPrimaryKey((long)10001);
        redisService.saveItemDetail(itemDetailDO);
    }

    //从redis读取一个数据
    @Test
    void test2(){
        ItemDetailDO itemDetail = redisService.getItemDetail(1);
        System.out.println(itemDetail);
        ItemDetailDO itemDetailDO = redisService.getItemDetail(10001);
        System.out.println(JSONObject.toJSONString(itemDetailDO));

        SkuQuantityDO skuQuantityDO = redisService.getSkuQuantity(10001001);
        System.out.println(JSONObject.toJSONString(skuQuantityDO));
        SkuDetailDO skuDetailDO = redisService.getSkuDetail(10001001);
        System.out.println(JSONObject.toJSONString(skuDetailDO));
    }

    @Test
    void test3(){
        List<SkuDetailDO> skuDetailDOList = redisService.getSkuDetailListByItemId(10002);
        System.out.println(JSONObject.toJSONString(skuDetailDOList.get(0)));
    }
}
