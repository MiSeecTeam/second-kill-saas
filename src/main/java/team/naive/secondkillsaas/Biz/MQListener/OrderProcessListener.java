package team.naive.secondkillsaas.Biz.MQListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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
@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
public class OrderProcessListener {

    private static final Logger log= LoggerFactory.getLogger(OrderProcessListener.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SkuQuantityMapperExt skuQuantityMapperExt;

    @RabbitHandler
    public void processOrder(OrderDO orderDO){
        //减库存
        Long skuId = orderDO.getSkuId();
        Long amount = orderDO.getAmount();
        skuQuantityMapperExt.decSkuQuantity(skuId, amount);

        //完成订单
        orderDO.setFinished(Boolean.TRUE);
        orderMapper.updateByPrimaryKey(orderDO);

        log.info("Finish processing order" + String.valueOf(orderDO.getOrderId()));
    }
}
