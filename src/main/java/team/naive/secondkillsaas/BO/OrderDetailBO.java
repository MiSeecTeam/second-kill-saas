package team.naive.secondkillsaas.BO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import team.naive.secondkillsaas.DO.ItemDetailDO;
import team.naive.secondkillsaas.DO.OrderDO;
import team.naive.secondkillsaas.DO.SkuDetailDO;

import java.util.Date;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDetailBO extends OrderBO {

    public OrderDetailBO(OrderDO orderDO, SkuDetailDO skuDetailDO, ItemDetailDO itemDetailDO) {
        super(orderDO);
        this.skuName = skuDetailDO.getSkuName();
        this.skuDesc = skuDetailDO.getSkuDesc();
        this.itemDesc = itemDetailDO.getItemDesc();
        this.itemName = itemDetailDO.getItemName();
    }

    private String skuName;

    private String skuDesc;

    private String itemName;

    private String itemDesc;

}