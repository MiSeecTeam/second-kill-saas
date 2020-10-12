package team.naive.secondkillsaas.Biz;

import team.naive.secondkillsaas.BO.ItemDetailBO;
import team.naive.secondkillsaas.BO.SkuDetailBO;
import team.naive.secondkillsaas.BO.SkuQuantityBO;

import java.util.List;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */
public interface ItemService {

    List<ItemDetailBO> listItemDetail();

    ItemDetailBO getItemDetail(Long itemId);

    SkuDetailBO getSkuDetail(Long skuId);

    SkuQuantityBO getSkuQuantity(Long skuId);
}
