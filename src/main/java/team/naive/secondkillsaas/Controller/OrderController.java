package team.naive.secondkillsaas.Controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.naive.secondkillsaas.BO.OrderBO;
import team.naive.secondkillsaas.Biz.OrderService;
import team.naive.secondkillsaas.Config.InterceptorConfiguration;
import team.naive.secondkillsaas.Form.KillForm;
import team.naive.secondkillsaas.VO.ResponseVO;
import team.naive.secondkillsaas.VO.UserForm;
import team.naive.secondkillsaas.VO.UserVO;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/10/16
 */
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/getOrderList")
    public ResponseVO getOrderList(@RequestParam Long userId) {
        return ResponseVO.buildSuccess(orderService.listOrders(userId));
    }

    @GetMapping("/getOrderDetail")
    public ResponseVO getOrderDetail(@RequestParam Long orderId) {
        return ResponseVO.buildSuccess(orderService.getOrderDetail(orderId));
    }

    @GetMapping("/getOrderDetailBak")
    public ResponseVO getOrderDetailBak(@RequestParam Long skuId, HttpSession session) {
        Integer userId = ((UserVO)session.getAttribute(InterceptorConfiguration.SESSION_KEY)).getId();
        return ResponseVO.buildSuccess(orderService.getOrderDetail(userId, skuId));
    }

    @GetMapping("/cancelOrder")
    public ResponseVO cancelOrder(@RequestParam Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    @PostMapping("/pay")
    public ResponseVO payForOrder(@RequestParam Long orderId) {
        return orderService.payForOrder(orderId);
    }
}
