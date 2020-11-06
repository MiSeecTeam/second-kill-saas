package team.naive.secondkillsaas.BO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */

@Data
public class ItemDetailBO {

    private Long itemId;

    private Date gmtCreated;

    private Date gmtModified;

    private Boolean isDeleted;

    private String itemName;

    private String itemDesc;

    private BigDecimal itemPrice;

}