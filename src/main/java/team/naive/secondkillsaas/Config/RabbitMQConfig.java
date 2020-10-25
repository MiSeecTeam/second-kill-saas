package team.naive.secondkillsaas.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ Description
 * @ Author YangYicun
 * @ Date 2020/10/21 23:25
 */
@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "DirectExchange";
    public static final String QUEUE_NAME = "OrderQueue";
    public static final String ROUTING_KEY = "routingKey";

    /**
     * 直连交换机
     */
    @Bean
    public DirectExchange getDirectExchange(){
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    /**
     * （订单）队列
     */
    @Bean
    public Queue getOrderQueue(){
        return new Queue(QUEUE_NAME, true, false, false);
    }

    /**
     * 将队列绑定到交换机
     */
    @Bean
    Binding binding(){
        return BindingBuilder.bind(getOrderQueue()).to(getDirectExchange()).with(ROUTING_KEY);
    }

}
