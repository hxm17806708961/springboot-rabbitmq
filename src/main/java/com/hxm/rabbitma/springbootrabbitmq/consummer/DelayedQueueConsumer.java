package com.hxm.rabbitma.springbootrabbitmq.consummer;

import com.hxm.rabbitma.springbootrabbitmq.appconfig.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 基于插件的延迟队列
 * @author hxmao
 * @date 2022/3/18 16:12
 */
@Component
@Slf4j
public class DelayedQueueConsumer {

    @RabbitListener(queues = DelayedQueueConfig.DELAY_QUEUE_NAME)
    public void receiveDelayedQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到延时队列的消息：{}", new Date().toString(), msg);
    }
}
