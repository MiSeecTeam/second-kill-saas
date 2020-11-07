package team.naive.secondkillsaas.Biz;

import team.naive.secondkillsaas.BO.ItemDetailBO;
import team.naive.secondkillsaas.VO.*;

import java.util.List;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */
public interface ItemService {

    List<ItemListItemVO> listItemDetail();

    ItemDetailBO getItemDetail(Long itemId);

    ItemSkuDetailVO getItemSkuDetailByItemId(Long itemId);

    /**
     * 添加商品
     * @param itemVO 商品信息
     * @return
     */
    ResponseVO addItem(ItemVO itemVO);

    /**
     * 添加sku
     * @param skuDetailVO
     * @return
     */
    ResponseVO addSku(SkuDetailVO skuDetailVO);

    /**
     * 获取所有商品及sku
     * @return
     */
    ResponseVO getAllItemSku();

    /**
     * 更新sku
     * @param skuId
     * @param amount
     * @return
     */
    ResponseVO updateSku(Long skuId, Long amount);

    /**
     * 删除sku
     * @param itemId
     * @return
     */
    ResponseVO deleteItem(Long itemId);

    /**
     * 获取item及sku数量
     * @return
     */
    ResponseVO getCount();
}
