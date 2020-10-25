package team.naive.secondkillsaas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.naive.secondkillsaas.Biz.OrderService;

/**
 * @ Description
 * @ Author YangYicun
 * @ Date 2020/10/23 13:19
 */
@SpringBootTest(classes = SecondKillSaasApplication.class)
public class RabbitMQTest {

    @Autowired
    private OrderService orderService;

    @Test
    void test1(){
        Long orderId = 10001L;
        orderService.payForOrder(orderId);

    }
}
