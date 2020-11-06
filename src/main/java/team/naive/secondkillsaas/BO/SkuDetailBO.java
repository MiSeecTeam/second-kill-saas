package team.naive.secondkillsaas.BO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SkuDetailBO {
    private Long skuId;

    private Date gmtCreated;

    private Date gmtModified;

    private Boolean isDeleted;

    private Long itemId;

    private String skuName;

    private String skuDesc;

    private BigDecimal skuPrice;

}