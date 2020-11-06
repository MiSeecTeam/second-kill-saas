package team.naive.secondkillsaas.VO;

import lombok.Data;
import team.naive.secondkillsaas.DO.SkuDetailDO;

import java.util.Date;
import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/11/6
 * @description:
 * 1、某商品的sku列表。
 * 2、价格最低到最高
 * 3、秒杀起止时间
 */
@Data
public class ItemSkuDetailVO {

    private Long itemId;

    private List<SkuDetailDO> skuDetailList;

    private double priceLow;
    private double priceHigh;

    private Date startTime;
    private Date endTime;
}
