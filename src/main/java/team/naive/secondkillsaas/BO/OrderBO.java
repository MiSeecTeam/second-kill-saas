package team.naive.secondkillsaas.BO;

import lombok.Data;
import team.naive.secondkillsaas.DO.OrderDO;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */

@Data
public class OrderBO implements Serializable {

    public OrderBO() {}

    public OrderBO(OrderDO orderDO) {
        this.amount = orderDO.getAmount();
        this.finished = orderDO.getFinished();
        this.gmtCreated = orderDO.getGmtCreated();
        this.gmtModified = orderDO.getGmtModified();
        this.skuId = orderDO.getSkuId();
        this.orderId = orderDO.getOrderId();
        this.isDeleted = orderDO.getIsDeleted();
        this.userId = orderDO.getUserId();
    }

    private Long orderId;

    private Date gmtCreated;

    private Date gmtModified;

    private Boolean isDeleted;

    private Long skuId;

    private Long amount;

    private Boolean finished;

    private Long userId;

}