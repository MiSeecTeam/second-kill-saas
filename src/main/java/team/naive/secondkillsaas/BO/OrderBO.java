package team.naive.secondkillsaas.BO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */

@Data
public class OrderBO implements Serializable {

    private Long orderId;

    private Date gmtCreated;

    private Date gmtModified;

    private Boolean isDeleted;

    private Long skuId;

    private Long amount;

    private Boolean finished;

}