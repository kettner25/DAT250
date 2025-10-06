package com.example.demo.Conf;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    public static final String POLL_EXCHANGE = "poll-topic-exchange";

    @Bean
    public TopicExchange pollExchange() {
        return new TopicExchange(POLL_EXCHANGE);
    }
}
