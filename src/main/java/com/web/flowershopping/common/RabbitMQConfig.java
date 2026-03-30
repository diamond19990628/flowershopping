package com.web.flowershopping.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue orderDelayQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 180000); // 3分钟
        args.put("x-dead-letter-exchange", "order.exchange");
        args.put("x-dead-letter-routing-key", "order.check");
        return new Queue("order.delay.queue", true, false, false, args);
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order.exchange");
        
    }

    @Bean
    public Queue orderCheckQueue() {
        return new Queue("order.check.queue", true);
    }

    @Bean
    public Binding orderCheckBinding() {
        return BindingBuilder.bind(orderCheckQueue())
                .to(orderExchange())
                .with("order.check");
    }
}
