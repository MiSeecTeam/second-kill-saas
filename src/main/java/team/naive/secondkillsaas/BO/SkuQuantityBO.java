package team.naive.secondkillsaas.BO;

import lombok.Data;

import java.util.Date;

@Data
public class SkuQuantityBO {
    private Long skuId;

    private Date gmtCreated;

    private Date gmtModified;

    private Boolean isDeleted;

    private Long amount;

    private Date startTime;

    private Date endTime;

}