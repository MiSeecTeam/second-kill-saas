package team.naive.secondkillsaas;

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

import java.util.Date;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/28
 * @description:
 */
public class KillServiceTest extends SecondKillSaasApplicationTests{
    @Autowired
    ItemDetailMapper itemDetailMapper;
    @Autowired
    SkuDetailMapper skuDetailMapper;
    @Autowired
    SkuQuantityMapper skuQuantityMapper;

    @Ignore
    @Test
    public void createANewItem(){
        ItemDetailDO itemDetailDO = new ItemDetailDO();
        itemDetailDO.setItemId((long) 10002);
        itemDetailDO.setGmtCreated(new Date());
        itemDetailDO.setGmtModified(itemDetailDO.getGmtCreated());
        itemDetailDO.setIsDeleted(false);
        itemDetailDO.setItemName("苹果X");
        itemDetailDO.setItemDesc("苹果便宜卖了。。");

        SkuDetailDO skuDetailDO = new SkuDetailDO();
        BeanUtils.copyProperties(itemDetailDO, skuDetailDO);
        skuDetailDO.setSkuId((long) 10002001);
        skuDetailDO.setSkuName("苹果X土豪金");
        skuDetailDO.setSkuDesc("彰显尊贵气质");

        SkuQuantityDO skuQuantityDO = new SkuQuantityDO();
        BeanUtils.copyProperties(skuDetailDO, skuQuantityDO);
        skuQuantityDO.setAmount((long) 999);
        skuQuantityDO.setStartTime(new Date());
        skuQuantityDO.setEndTime(new Date(skuQuantityDO.getStartTime().getTime()+(long)30*24*60*60*1000));

        itemDetailMapper.insert(itemDetailDO);
        skuDetailMapper.insert(skuDetailDO);
        skuQuantityMapper.insert(skuQuantityDO);
    }
}
