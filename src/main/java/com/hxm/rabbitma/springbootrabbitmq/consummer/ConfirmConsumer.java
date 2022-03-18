package com.hxm.rabbitma.springbootrabbitmq.consummer;

import com.hxm.rabbitma.springbootrabbitmq.appconfig.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author hxmao
 * @date 2022/3/18 16:52
 */
@Slf4j
@Component
public class ConfirmConsumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE)
    public void confirmMessage(Message message){

        log.info("发布确认接收到的消息 ： {}" , new String(message.getBody()));

    }
}
