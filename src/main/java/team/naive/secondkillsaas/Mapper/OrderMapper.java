package team.naive.secondkillsaas.Mapper;

import java.util.List;
import team.naive.secondkillsaas.DO.OrderDO;
import team.naive.secondkillsaas.DO.OrderDOExample;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(OrderDO record);

    int insertSelective(OrderDO record);

    List<OrderDO> selectByExample(OrderDOExample example);

    OrderDO selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);
}