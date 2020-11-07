package team.naive.secondkillsaas.VO;

import lombok.Data;
import team.naive.secondkillsaas.DO.SkuDetailDO;

import java.util.Date;
import java.util.List;

/**
 * @author WangYuxiao
 * @date 2020/11/6 22:10
 */
@Data
public class ItemSkuFormVO {

    private List<SkuDetailVO> skuDetailList;

    private Long itemId;

    private Date gmtCreated;

    private Date gmtModified;

    private Boolean isDeleted;

    private String itemName;

    private String itemDesc;
}
