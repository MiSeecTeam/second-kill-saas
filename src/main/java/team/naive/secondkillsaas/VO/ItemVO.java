package team.naive.secondkillsaas.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WangYuxiao
 * @date 2020/11/6 19:06
 */
@Data
public class ItemVO {

    private Long itemId;

    private Date gmtCreated;

    private Date gmtModified;

    private Boolean isDeleted;

    private String itemName;

    private String itemDesc;

}
