package team.naive.secondkillsaas.Mapper;

import java.util.List;
import team.naive.secondkillsaas.DO.SkuQuantityDO;
import team.naive.secondkillsaas.DO.SkuQuantityDOExample;

public interface SkuQuantityMapper {
    int deleteByPrimaryKey(Long skuId);

    int insert(SkuQuantityDO record);

    int insertSelective(SkuQuantityDO record);

    List<SkuQuantityDO> selectByExample(SkuQuantityDOExample example);

    SkuQuantityDO selectByPrimaryKey(Long skuId);

    int updateByPrimaryKeySelective(SkuQuantityDO record);

    int updateByPrimaryKey(SkuQuantityDO record);
}