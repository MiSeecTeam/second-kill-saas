package team.naive.secondkillsaas.Mapper;

import java.util.List;
import team.naive.secondkillsaas.DO.ItemDetailDO;
import team.naive.secondkillsaas.DO.ItemDetailDOExample;

public interface ItemDetailMapper {
    int deleteByPrimaryKey(Long itemId);

    int insert(ItemDetailDO record);

    int insertSelective(ItemDetailDO record);

    List<ItemDetailDO> selectByExampleWithBLOBs(ItemDetailDOExample example);

    List<ItemDetailDO> selectByExample(ItemDetailDOExample example);

    ItemDetailDO selectByPrimaryKey(Long itemId);

    int updateByPrimaryKeySelective(ItemDetailDO record);

    int updateByPrimaryKeyWithBLOBs(ItemDetailDO record);

    int updateByPrimaryKey(ItemDetailDO record);
}