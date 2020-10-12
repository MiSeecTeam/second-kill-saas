package team.naive.secondkillsaas.Mapper;

import java.util.List;
import team.naive.secondkillsaas.DO.SkuDetailDO;
import team.naive.secondkillsaas.DO.SkuDetailDOExample;

public interface SkuDetailMapper {
    int deleteByPrimaryKey(Long skuId);

    int insert(SkuDetailDO record);

    int insertSelective(SkuDetailDO record);

    List<SkuDetailDO> selectByExampleWithBLOBs(SkuDetailDOExample example);

    List<SkuDetailDO> selectByExample(SkuDetailDOExample example);

    SkuDetailDO selectByPrimaryKey(Long skuId);

    int updateByPrimaryKeySelective(SkuDetailDO record);

    int updateByPrimaryKeyWithBLOBs(SkuDetailDO record);

    int updateByPrimaryKey(SkuDetailDO record);
}