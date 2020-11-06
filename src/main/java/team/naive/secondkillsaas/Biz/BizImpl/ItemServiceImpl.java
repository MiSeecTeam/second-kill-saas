package team.naive.secondkillsaas.Biz.BizImpl;/**
 * Created by Administrator on 2019/6/16.
 */


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.naive.secondkillsaas.BO.ItemDetailBO;
import team.naive.secondkillsaas.BO.SkuDetailBO;
import team.naive.secondkillsaas.BO.SkuQuantityBO;
import team.naive.secondkillsaas.DO.ItemDetailDO;
import team.naive.secondkillsaas.DO.ItemDetailDOExample;
import team.naive.secondkillsaas.Biz.ItemService;
import team.naive.secondkillsaas.DO.SkuDetailDO;
import team.naive.secondkillsaas.DO.SkuQuantityDO;
import team.naive.secondkillsaas.Mapper.ItemDetailMapper;
import team.naive.secondkillsaas.Redis.RedisService;
import team.naive.secondkillsaas.Mapper.SkuDetailMapper;
import team.naive.secondkillsaas.Mapper.SkuQuantityMapper;
import team.naive.secondkillsaas.VO.ItemListItemVO;
import team.naive.secondkillsaas.VO.ItemSkuDetailVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */
@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger log= LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private ItemDetailMapper itemDetailMapper;

    @Autowired
    private SkuDetailMapper skuDetailMapper;

    @Autowired
    private SkuQuantityMapper skuQuantityMapper;

    /**
     * 获得商品列表，包含价格属性
     * @return
     */
    @Override
    public List<ItemListItemVO> listItemDetail() {
        List<ItemListItemVO> itemList = new ArrayList<>();
        Map<Object, Object> allItemDetail = redisService.getAllItemDetail();
        for(Object object: allItemDetail.values()){
            /*
            填充itemListItemVO
             */
            ItemListItemVO itemListItemVO = new ItemListItemVO();
            BeanUtils.copyProperties(object, itemListItemVO);
            //获得最低价与最高价
            List<SkuDetailDO> skuDetailList = redisService.getSkuDetailListByItemId(itemListItemVO.getItemId());
            double[] price = getPriceOfItem(skuDetailList);
            itemListItemVO.setPriceLow(price[0]);
            itemListItemVO.setPriceHigh(price[1]);

            itemList.add(itemListItemVO);
        }

        return itemList;
    }

    @Override
    public ItemDetailBO getItemDetail(Long id) {
        ItemDetailBO itemDetailBO = new ItemDetailBO();
        BeanUtils.copyProperties(redisService.getItemDetail(id), itemDetailBO);
        return itemDetailBO;
    }

    @Override
    public ItemSkuDetailVO getItemSkuDetailByItemId(Long itemId) {

        ItemSkuDetailVO result = new ItemSkuDetailVO();
        result.setItemId(itemId);

        List<SkuDetailDO> skuDetailList = redisService.getSkuDetailListByItemId(itemId);
        result.setSkuDetailList(skuDetailList);
        double[] price = getPriceOfItem(skuDetailList);
        //设置价格
        result.setPriceLow(price[0]);
        result.setPriceHigh(price[1]);
        //设置秒杀起止时间
        if(skuDetailList.size()!=0){
            long skuId = skuDetailList.get(0).getSkuId();
            SkuQuantityDO skuQuantity = redisService.getSkuQuantity(skuId);
            result.setStartTime(skuQuantity.getStartTime());
            result.setEndTime(skuQuantity.getEndTime());
        }

        return result;
    }

    public SkuDetailBO getSkuDetail(Long id) {
        SkuDetailBO skuDetailBO = new SkuDetailBO();
        BeanUtils.copyProperties(redisService.getSkuDetail(id), skuDetailBO);
        return skuDetailBO;
    }


    public SkuQuantityBO getSkuQuantity(Long id) {
        SkuQuantityBO skuQuantityBO = new SkuQuantityBO();
        BeanUtils.copyProperties(redisService.getSkuQuantity(id), skuQuantityBO);
        return skuQuantityBO;
    }

    /**
     * 获得某商品的价格
     * @param skuDetailList
     * @return {price_low, price_high}
     */
    private double[] getPriceOfItem(List<SkuDetailDO> skuDetailList){
        double price_low = 0;
        double price_high = 0;
        if(skuDetailList.size()==0){
            //该产品没sku，不设置价格
        }
        else{
            price_low = skuDetailList.get(0).getSkuPrice().doubleValue();
            price_high = skuDetailList.get(0).getSkuPrice().doubleValue();
            for(SkuDetailDO skuDetailDO: skuDetailList){
                if(skuDetailDO.getSkuPrice().doubleValue()>price_high){
                    price_high = skuDetailDO.getSkuPrice().doubleValue();
                }
                if(skuDetailDO.getSkuPrice().doubleValue()<price_low){
                    price_low = skuDetailDO.getSkuPrice().doubleValue();
                }
            }
        }
        return new double[]{price_low, price_high};
    }



}



















