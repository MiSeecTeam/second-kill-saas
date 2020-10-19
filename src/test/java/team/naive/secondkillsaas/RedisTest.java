package team.naive.secondkillsaas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import team.naive.secondkillsaas.DO.ItemDetailDO;
import team.naive.secondkillsaas.Mapper.ItemDetailMapper;
import team.naive.secondkillsaas.Redis.RedisMapper;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/19
 * @description:
 */
public class RedisTest extends SecondKillSaasApplicationTests {

    @Autowired
    RedisMapper redisMapper;
    @Autowired
    ItemDetailMapper itemDetailMapper;


    //向redis填一条数据
    @Test
    void test1(){
        ItemDetailDO itemDetailDO = itemDetailMapper.selectByPrimaryKey((long)1);
        redisMapper.saveItemDetail(itemDetailDO);
    }

    @Test
    void test2(){
        ItemDetailDO itemDetail = redisMapper.getItemDetail(1);
        System.out.println(itemDetail);
    }
}
