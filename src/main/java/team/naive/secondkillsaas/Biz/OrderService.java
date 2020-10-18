package team.naive.secondkillsaas.Biz;

import team.naive.secondkillsaas.BO.*;

import java.util.List;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */
public interface OrderService {

    List<OrderBO> listOrders(Long userId);

    OrderDetailBO getOrderDetail(Long orderId);
}
