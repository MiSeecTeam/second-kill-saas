package team.naive.secondkillsaas.VO;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/11/6
 * @description:
 */

import lombok.Data;

import java.util.Date;

/**
 * 商品列表项
 * 比ItemDetailDO多一个价格属性[多少到多少]
 */
@Data
public class ItemListItemVO {
    private Long itemId;

    private Date gmtCreated;

    private Date gmtModified;

    private Boolean isDeleted;

    private String itemName;

    private String itemDesc;

    private double priceLow;

    private double priceHigh;
}
