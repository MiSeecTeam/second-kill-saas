package team.naive.secondkillsaas.Mapper;

//import org.springframework.data.repository.query.Param;
import org.apache.ibatis.annotations.Param;

/**
 * @ Description
 * @ Author YangYicun
 * @ Date 2020/10/22 21:06
 */
public interface SkuQuantityMapperExt extends SkuQuantityMapper {

    /**
     * 订单完成后减库存
     * @param skuId
     * @return
     */
    int decSkuQuantity(@Param("skuId") Long skuId, @Param("amount") Long amount);
}
