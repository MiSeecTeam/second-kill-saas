package team.naive.secondkillsaas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.naive.secondkillsaas.BO.OrderBO;
import team.naive.secondkillsaas.Biz.MQMonitorService;
import team.naive.secondkillsaas.Biz.OrderService;
import team.naive.secondkillsaas.VO.ResponseVO;

/**
 * @ Description
 * @ Author YangYicun
 * @ Date 2020/10/23 13:19
 */
@SpringBootTest(classes = SecondKillSaasApplication.class)
public class RabbitMQTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MQMonitorService mqMonitorService;

    @Test
    void test1(){
        Long orderId = 10001L;
        OrderBO orderBO = (OrderBO) orderService.payForOrder(orderId).getContent();
//        System.out.println(orderBO);
    }

    @Test
    void mqMonitorTest(){
//        String res = (String) mqMonitorService.getMonitorStat().getContent();
        System.out.println(mqMonitorService.getMonitorStat().getContent());
    }
}
