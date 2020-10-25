package team.naive.secondkillsaas.Biz.BizImpl;/**
 * Created by Administrator on 2019/6/16.
 */


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
import team.naive.secondkillsaas.Mapper.ItemDetailMapper;
import team.naive.secondkillsaas.Redis.RedisService;
import team.naive.secondkillsaas.Mapper.SkuDetailMapper;
import team.naive.secondkillsaas.Mapper.SkuQuantityMapper;

import java.util.List;
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

    @Override
    public List<ItemDetailBO> listItemDetail() {
        ItemDetailDOExample example = new ItemDetailDOExample();
        ItemDetailDOExample.Criteria criteria = example.createCriteria();
        // todo:根据criteria实现更复杂的list查询
        criteria.andGmtCreatedIsNotNull();
        List<ItemDetailDO> itemDetailDOList = itemDetailMapper.selectByExample(example);

        return itemDetailDOList.stream().map(itemDetailDO -> {
            ItemDetailBO itemDetailBO = new ItemDetailBO();
            BeanUtils.copyProperties(itemDetailDO, itemDetailBO);
            return itemDetailBO;
        }).collect(Collectors.toList());
    }

    @Override
    public ItemDetailBO getItemDetail(Long id) {
        ItemDetailBO itemDetailBO = new ItemDetailBO();
        BeanUtils.copyProperties(redisService.getItemDetail(id), itemDetailBO);
        return itemDetailBO;
    }


    @Override
    public SkuDetailBO getSkuDetail(Long id) {
        SkuDetailBO skuDetailBO = new SkuDetailBO();
        BeanUtils.copyProperties(redisService.getSkuDetail(id), skuDetailBO);
        return skuDetailBO;
    }

    @Override
    public SkuQuantityBO getSkuQuantity(Long id) {
        SkuQuantityBO skuQuantityBO = new SkuQuantityBO();
        BeanUtils.copyProperties(redisService.getSkuQuantity(id), skuQuantityBO);
        return skuQuantityBO;
    }


}



















