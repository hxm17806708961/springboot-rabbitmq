package com.hxm.rabbitma.springbootrabbitmq.appconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 插件延迟队列
 * @author hxmao
 * @date 2022/3/18 15:44
 */
@Configuration
public class DelayedQueueConfig {

    // 队列名称
    public static final String DELAY_QUEUE_NAME = "delay.queue";
    // 交换机名称
    public static final String DELAY_EXCHANGE_NAME = "delay.exchange";
    // routingkey 名称
    public static final String DELAY_ROUTING_KEY = "delay.routingkey";

    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAY_QUEUE_NAME);
    }

    @Bean
    public CustomExchange customExchange(){
        Map<String, Object> arguments = new HashMap<>();
        //自定义交换机的类型
        arguments.put("x-delayed-type", "direct");

        return new CustomExchange(DELAY_EXCHANGE_NAME, "x-delayed-message",true, false, arguments);
    }

    @Bean
    public Binding bingdQueue(){
        return BindingBuilder.bind(delayedQueue()).to(customExchange()).with(DELAY_ROUTING_KEY).noargs();
    }

}
