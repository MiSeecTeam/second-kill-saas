package team.naive.secondkillsaas.Biz.BizImpl;/**
 * Created by Administrator on 2019/6/16.
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.naive.secondkillsaas.BO.*;
import team.naive.secondkillsaas.Biz.ItemService;
import team.naive.secondkillsaas.Biz.OrderService;
import team.naive.secondkillsaas.Config.RabbitMQConfig;
import team.naive.secondkillsaas.DO.ItemDetailDO;
import team.naive.secondkillsaas.DO.ItemDetailDOExample;
import team.naive.secondkillsaas.DO.OrderDO;
import team.naive.secondkillsaas.Mapper.ItemDetailMapper;
import team.naive.secondkillsaas.Mapper.OrderMapper;
import team.naive.secondkillsaas.Mapper.SkuDetailMapper;
import team.naive.secondkillsaas.Mapper.SkuQuantityMapper;
import team.naive.secondkillsaas.VO.ResponseVO;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log= LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMapper orderMapper;

    private static final String ORDER_CANCELED_INFO = "订单已被取消";
    @Override
    public List<OrderBO> listOrders(Long userId){
        return null;
    };

    @Override
    public OrderDetailBO getOrderDetail(Long orderId){
        return null;
    };

    @Override
    public ResponseVO payForOrder(Long orderId){
        OrderDO orderDO = orderMapper.selectByPrimaryKey(orderId);
        if(orderDO.getIsDeleted()){//若订单已被取消
            return ResponseVO.buildFailure(ORDER_CANCELED_INFO);
        }

        //转换成序列化对象BO，发送到消息队列，并用于返回前端
        OrderBO orderBO = new OrderBO();
        BeanUtils.copyProperties(orderDO, orderBO);
        //发送订单id至消息队列
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, orderBO);

        return ResponseVO.buildSuccess(orderBO);
    }

}



















