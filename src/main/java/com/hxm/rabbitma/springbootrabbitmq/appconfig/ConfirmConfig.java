package com.hxm.rabbitma.springbootrabbitmq.appconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hxmao
 * @date 2022/3/18 16:32
 */
@Configuration
public class ConfirmConfig {
    // 队列名称
    public static final String CONFIRM_QUEUE = "confirm.queue";
    // 交换机名称
    public static final String CONFIRM_EXCHANGE = "confirm.exchange";
    // routingkey 名称
    public static final String CONFIRM_ROUTING_KEY = "confirm.routingkey";

    // 配备交换机
    public static final String BACKUP_EXCHANGE = "backup.exchange";
    // 配备队列
    public static final String BACKUP_QUEUE = "backup.queue";
    // 报警队列
    public static final String WARNING_QUEUE = "warning.queue";

    @Bean
    public Queue confirmQueue(){
        return new Queue(CONFIRM_QUEUE);
    }

    /**
     * 直接交换机，启用备份交换机
     * @return
     */
    @Bean
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE).durable(true)
                .withArgument("alternate-exchange", BACKUP_EXCHANGE).build();
    }

    @Bean
    public Binding confirmBinding(){
        return BindingBuilder.bind(confirmQueue()).to(confirmExchange()).with(CONFIRM_ROUTING_KEY);
    }

    @Bean
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE);
    }

    @Bean
    public Queue backupQueue(){
        return new Queue(BACKUP_QUEUE);
    }

    @Bean
    public Queue warningQueue(){
        return new Queue(WARNING_QUEUE);
    }

    @Bean
    public Binding backupQueueBindingBackupExchange(){
        return BindingBuilder.bind(backupQueue()).to(backupExchange());
    }

    @Bean
    public Binding warningQueueBindingBackupExchange(){
        return BindingBuilder.bind(warningQueue()).to(backupExchange());
    }
}
