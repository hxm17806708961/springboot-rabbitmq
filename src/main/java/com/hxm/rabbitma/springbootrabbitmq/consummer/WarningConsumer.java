package com.hxm.rabbitma.springbootrabbitmq.consummer;

import com.hxm.rabbitma.springbootrabbitmq.appconfig.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author hxmao
 * @date 2022/3/18 17:56
 */
@Slf4j
@Component
public class WarningConsumer {

    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE)
    public void warning(Message message){

        log.warn("报警确认接收到的消息 ： {}" , new String(message.getBody()));

    }
}
