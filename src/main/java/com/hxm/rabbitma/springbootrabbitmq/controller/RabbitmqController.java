package com.hxm.rabbitma.springbootrabbitmq.controller;

import com.hxm.rabbitma.springbootrabbitmq.appconfig.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 生成者
 * 生成数据
 * @author hxmao
 * @date 2022/3/17 15:53
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class RabbitmqController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendMsg/{message}")
    public String getMessage(@PathVariable(value = "message") String message){

        log.info("接收时间 {}, 发送信息给两个ttl队列： {}", new Date().toString(), message);

        rabbitTemplate.convertAndSend("X", "XA", "10秒队列" + message);
        rabbitTemplate.convertAndSend("X", "XB", "40秒队列" + message);

        return null;
    }

    @RequestMapping(value = "/sendMsg/{message}/{date}")
    public String send(@PathVariable("message") String message, @PathVariable(value = "date") String date ){

        rabbitTemplate.convertAndSend("X", "XC", message, correlationData -> {
            correlationData.getMessageProperties().setExpiration(date);
            return correlationData;
        });
        log.info("当前时间：{},发送一条时长{}毫秒 TTL 信息给队列 C:{}", new Date(),date, message);

        return "延迟推送";
    }

    @RequestMapping(value = "/sendDelayedMsg/{message}/{date}")
    public String sendDelayMessage(@PathVariable("message") String message, @PathVariable(value = "date") String date ){

        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAY_EXCHANGE_NAME, DelayedQueueConfig.DELAY_ROUTING_KEY, message, correlationData -> {
            correlationData.getMessageProperties().setDelay(Integer.parseInt(date));
            return correlationData;
        });
        log.info("当前时间：{},发送一条时长{}毫秒 TTL 信息给队列 C:{}", new Date(),date, message);

        return "插件延迟推送";
    }

}
