package team.naive.secondkillsaas.Biz.MQListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.naive.secondkillsaas.BO.OrderBO;
import team.naive.secondkillsaas.Biz.BizImpl.ItemServiceImpl;
import team.naive.secondkillsaas.Config.RabbitMQConfig;
import team.naive.secondkillsaas.DO.OrderDO;
import team.naive.secondkillsaas.DO.SkuQuantityDO;
import team.naive.secondkillsaas.Mapper.OrderMapper;
import team.naive.secondkillsaas.Mapper.SkuQuantityMapperExt;

/**
 * @ Description
 * @ Author YangYicun
 * @ Date 2020/10/22 20:31
 */
@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
public class OrderProcessListener {

    private static final Logger log= LoggerFactory.getLogger(OrderProcessListener.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SkuQuantityMapperExt skuQuantityMapperExt;

    @RabbitHandler
    public void processOrder(OrderBO orderBO){
        //减库存
        Long skuId = orderBO.getSkuId();
        Long amount = orderBO.getAmount();
        skuQuantityMapperExt.decSkuQuantity(skuId, amount);

        //完成订单
        orderBO.setFinished(Boolean.TRUE);
        //转换成DO，更新数据库
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderBO, orderDO);
        orderMapper.updateByPrimaryKey(orderDO);

        log.info("Finish processing order" + String.valueOf(orderBO.getOrderId()));
//        System.out.println("Finish processing order" + String.valueOf(orderBO.getOrderId()));
    }
}
