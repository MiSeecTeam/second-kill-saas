package team.naive.secondkillsaas;

import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import team.naive.secondkillsaas.DO.ItemDetailDO;
import team.naive.secondkillsaas.DO.SkuDetailDO;
import team.naive.secondkillsaas.DO.SkuQuantityDO;
import team.naive.secondkillsaas.Mapper.ItemDetailMapper;
import team.naive.secondkillsaas.Mapper.SkuDetailMapper;
import team.naive.secondkillsaas.Mapper.SkuQuantityMapper;
import team.naive.secondkillsaas.Redis.RedisService;

import java.util.Date;
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
    @Autowired
    SkuDetailMapper skuDetailMapper;
    @Autowired
    SkuQuantityMapper skuQuantityMapper;


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

    /**
     * 生成用于测试的商品，库存20
     */
    @Ignore
    @Test
    void test4(){
        ItemDetailDO itemDetailDO = new ItemDetailDO();
        itemDetailDO.setItemId((long) 10005);
        itemDetailDO.setGmtCreated(new Date());
        itemDetailDO.setGmtModified(itemDetailDO.getGmtCreated());
        itemDetailDO.setIsDeleted(false);
        itemDetailDO.setItemName("压力测试商品");
        itemDetailDO.setItemDesc("用于压力测试");

        SkuDetailDO skuDetailDO = new SkuDetailDO();
        BeanUtils.copyProperties(itemDetailDO, skuDetailDO);
        skuDetailDO.setSkuId((long) 10005001);
        skuDetailDO.setSkuName("压力测试Sku");
        skuDetailDO.setSkuDesc("用于压力测试");

        SkuQuantityDO skuQuantityDO = new SkuQuantityDO();
        BeanUtils.copyProperties(skuDetailDO, skuQuantityDO);
        skuQuantityDO.setAmount((long) 20);
        skuQuantityDO.setStartTime(new Date());
        skuQuantityDO.setEndTime(new Date(skuQuantityDO.getStartTime().getTime()+(long)30*24*60*60*1000));

        itemDetailMapper.insert(itemDetailDO);
        skuDetailMapper.insert(skuDetailDO);
        skuQuantityMapper.insert(skuQuantityDO);
    }
}
