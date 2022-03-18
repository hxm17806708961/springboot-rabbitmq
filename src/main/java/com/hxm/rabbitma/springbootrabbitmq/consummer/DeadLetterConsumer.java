package com.hxm.rabbitma.springbootrabbitmq.consummer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 消费者，消费数据
 *
 * @author hxmao
 * @date 2022/3/17 16:03
 */
@Slf4j
@Component
public class DeadLetterConsumer {

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel){
        String string = message.getBody().toString();

        log.info("收到死信队列的时间：{}，消息 ： {}", new Date().toString(), string);

    }

}
