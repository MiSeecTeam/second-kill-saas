package team.naive.secondkillsaas.BO;

import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */

@Data
public class OrderDetailBO {

    private Long orderId;

    private Date gmtCreated;

    private Date gmtModified;

    private Boolean isDeleted;

    private Long skuId;

    private String skuName;

    private String skuDesc;

    private Long amount;

    private String itemName;

    private String itemDesc;

    private Boolean finished;

}