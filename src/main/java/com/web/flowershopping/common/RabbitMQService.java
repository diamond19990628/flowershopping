package com.web.flowershopping.common;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String orderNo) {
    rabbitTemplate.convertAndSend("order.delay.queue", orderNo);
}
}
