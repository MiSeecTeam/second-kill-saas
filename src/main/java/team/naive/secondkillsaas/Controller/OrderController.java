package team.naive.secondkillsaas.Controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.naive.secondkillsaas.Biz.OrderService;
import team.naive.secondkillsaas.Form.KillForm;
import team.naive.secondkillsaas.VO.ResponseVO;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/10/16
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/getOrderList")
    public ResponseVO getOrderList(@RequestParam Long userId) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setContent(orderService.getOrderDetail(userId));
        return  responseVO;
    }
}
