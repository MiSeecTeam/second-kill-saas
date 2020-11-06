package team.naive.secondkillsaas.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WangYuxiao
 * @date 2020/11/6 20:06
 */
@Data
public class SkuDetailVO {

    private Long skuId;

    private Date gmtCreated;

    private Date gmtModified;

    private Boolean isDeleted;

    private Long itemId;

    private String skuName;

    private BigDecimal skuPrice;

    private String skuDesc;

    private Date startTime;

    private Date endTime;

    private Long amount;

}
