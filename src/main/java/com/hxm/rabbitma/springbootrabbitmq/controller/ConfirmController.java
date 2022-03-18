package com.hxm.rabbitma.springbootrabbitmq.controller;

import com.hxm.rabbitma.springbootrabbitmq.appconfig.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hxmao
 * @date 2022/3/18 16:47
 */
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ConfirmController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("sendMsg/{message}")
    public String confirm(@PathVariable("message") String message){

        CorrelationData correlationData = new CorrelationData("1");
        CorrelationData correlationData2 = new CorrelationData("2");

        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE, ConfirmConfig.CONFIRM_ROUTING_KEY, message, correlationData);
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE, ConfirmConfig.CONFIRM_ROUTING_KEY + "fd", message, correlationData2);

        log.info("发布确认 消息： {}", message);
        return "发布确认";
    }
}
