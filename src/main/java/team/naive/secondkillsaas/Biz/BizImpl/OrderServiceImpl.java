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
import team.naive.secondkillsaas.Biz.OrderServiceForBiz;
import team.naive.secondkillsaas.Config.RabbitMQConfig;
import team.naive.secondkillsaas.DO.*;
import team.naive.secondkillsaas.DTO.OrderFormDTO;
import team.naive.secondkillsaas.Mapper.ItemDetailMapper;
import team.naive.secondkillsaas.Mapper.OrderMapper;
import team.naive.secondkillsaas.Mapper.SkuDetailMapper;
import team.naive.secondkillsaas.Mapper.SkuQuantityMapper;
import team.naive.secondkillsaas.Redis.RedisService;
import team.naive.secondkillsaas.Utils.RedisUtils;
import team.naive.secondkillsaas.VO.ResponseVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */
@Service
public class OrderServiceImpl implements OrderService, OrderServiceForBiz {

    private static final Logger log= LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SkuDetailMapper skuDetailMapper;

    @Autowired
    private ItemDetailMapper itemDetailMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String ORDER_CANCELED_INFO = "订单已被取消";
    private static final String ORDER_CREATE_FAILURE = "创建订单失败";
    private static final String ORDER_CANCEL_FAILURE = "取消订单失败";
    private static final String ORDER_ID_NOT_EXIST = "订单号不存在";
    private static final String ORDER_LOCK_PREFIX = "ORDER_LOCK_PREFIX";

    @Override
    public List<OrderBO> listOrders(Long userId){
        if (userId == null) {
            return null;
        }
        OrderDOExample example = new OrderDOExample();
        OrderDOExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<OrderDO> orderDOList = orderMapper.selectByExample(example);
        List<OrderBO> orderBOList = new ArrayList<>();
        for (OrderDO order: orderDOList) {
            OrderBO orderBO = new OrderBO();
            BeanUtils.copyProperties(order, orderBO);
            orderBOList.add(orderBO);
        }
        return orderBOList;
    }

    @Override
    public OrderDetailBO getOrderDetail(Long orderId){
        if (orderId == null) {
            return null;
        }

        OrderDO orderDO = orderMapper.selectByPrimaryKey(orderId);
        long skuId = orderDO.getSkuId();
        SkuDetailDO skuDetailDO = redisService.getSkuDetail(skuId);
        if (skuDetailDO == null) {
            skuDetailDO = skuDetailMapper.selectByPrimaryKey(skuId);
            if (skuDetailDO == null) {
                return null;
            }
        }
        long itemId = skuDetailDO.getItemId();
        ItemDetailDO itemDetailDO = redisService.getItemDetail(itemId);
        if (itemDetailDO == null) {
            itemDetailDO = itemDetailMapper.selectByPrimaryKey(itemId);
            if (itemDetailDO == null) {
                return null;
            }
        }
        return new OrderDetailBO(orderDO, skuDetailDO, itemDetailDO);
    }

    @Override
    public OrderBO getOrderDetail(Integer userId, Long skuId) {
        if (userId == null || skuId == null) {
            return null;
        }
        OrderDOExample example = new OrderDOExample();
        OrderDOExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId.longValue()).andSkuIdEqualTo(skuId);
        List<OrderDO> orderDOList = orderMapper.selectByExample(example);
        if (orderDOList.size() == 0) {
            return null;
        }
        OrderBO orderBO = new OrderBO();
        BeanUtils.copyProperties(orderDOList.get(0), orderBO);
        return orderBO;
    }

    @Override
    public ResponseVO cancelOrder(Long orderId) {
        if (orderId == null) {
            return null;
        }

        // todo: 适应redis分桶
        try {
            while (true) {
                if (tryLock(orderId)) {
                    OrderDO orderDO = orderMapper.selectByPrimaryKey(orderId);
                    if (orderDO.getIsDeleted()) {
                        unlock(orderId);
                        return ResponseVO.buildSuccess("订单已删除");
                    }
                    orderDO.setIsDeleted(true);
                    orderMapper.updateByPrimaryKey(orderDO);

                    SkuQuantityDO skuQuantityDO = redisService.getSkuQuantity(orderDO.getSkuId());
                    long amount = skuQuantityDO.getAmount();
                    amount++;
                    skuQuantityDO.setAmount(amount);
                    redisService.saveSkuQuantity(skuQuantityDO);


                    break;
                }
            }
            unlock(orderId);
            return ResponseVO.buildSuccess("订单已删除");
        } catch (Exception e) {
            return ResponseVO.buildFailure(ORDER_CANCEL_FAILURE);
        }

    }

    @Override
    public ResponseVO payForOrder(Long orderId){
        OrderDO orderDO = orderMapper.selectByPrimaryKey(orderId);
        // 若订单已被取消
        if(orderDO.getIsDeleted()){
            return ResponseVO.buildFailure(ORDER_CANCELED_INFO);
        }

        //转换成序列化对象BO，发送到消息队列，并用于返回前端
        OrderBO orderBO = new OrderBO();
        BeanUtils.copyProperties(orderDO, orderBO);
        //发送订单id至消息队列
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, orderBO);

        return ResponseVO.buildSuccess(orderBO);
    }

    @Override
    public ResponseVO createOrder(OrderFormDTO orderFormDTO) {
        OrderDO orderDO = new OrderDO();
        // 目前抢购数量默认为1
        orderDO.setAmount(1L);
        orderDO.setFinished(false);
        orderDO.setGmtCreated(new Date());
        orderDO.setGmtModified(new Date());
        orderDO.setSkuId(orderFormDTO.getSkuId());
        orderDO.setUserId(orderFormDTO.getUserId());
        orderDO.setIsDeleted(false);

        try {
            long affectRow = orderMapper.insert(orderDO);
//            orderDO.setOrderId(orderId);
            OrderBO orderBO = new OrderBO();
            BeanUtils.copyProperties(orderDO, orderBO);
            return ResponseVO.buildSuccess(orderBO);
        } catch (Exception e) {
            return ResponseVO.buildFailure(ORDER_CREATE_FAILURE);
        }
    }

    private boolean tryLock(Long orderId)  {
        return redisUtils.setIfAbsent(ORDER_LOCK_PREFIX + orderId);
    }

    private void unlock(Long orderId) {
        redisUtils.del(ORDER_LOCK_PREFIX + orderId);
    }

}



















