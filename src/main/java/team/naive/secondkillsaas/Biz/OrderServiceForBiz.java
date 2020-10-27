package team.naive.secondkillsaas.Biz;

import team.naive.secondkillsaas.DTO.OrderFormDTO;
import team.naive.secondkillsaas.VO.ResponseVO;

/**
 * @author WangYuxiao
 * @date 2020/10/25 23:20
 */
public interface OrderServiceForBiz {
    /**
     * 提供给其他服务的接口，创建订单
     * @param orderFormDTO 订单
     * @return
     */
    public ResponseVO createOrder(OrderFormDTO orderFormDTO);
}
