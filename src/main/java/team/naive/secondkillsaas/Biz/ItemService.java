package team.naive.secondkillsaas.Biz;

import team.naive.secondkillsaas.BO.ItemDetailBO;
import team.naive.secondkillsaas.BO.SkuDetailBO;
import team.naive.secondkillsaas.BO.SkuQuantityBO;
import team.naive.secondkillsaas.VO.ItemListItemVO;
import team.naive.secondkillsaas.VO.ItemSkuDetailVO;

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
}
