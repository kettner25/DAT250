package com.example.demo.Services;

import com.example.demo.Models.Poll;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
public class MessageManager {

    private final AmqpAdmin amqpAdmin;
    private final TopicExchange topicExch;
    private final ConnectionFactory connFactory;

    public MessageManager(AmqpAdmin _amqpAdmin, TopicExchange _topicExchange, ConnectionFactory _connFac) {
        this.amqpAdmin = _amqpAdmin;
        this.topicExch = _topicExchange;
        this.connFactory = _connFac;
    }

    public void RegisterAndSubscribeTopic(Poll poll) {
        registerPollTopic(poll);
        subscribeToPollTopic(poll);
    }

    public void registerPollTopic(Poll poll) {
        String queueName = "poll-" + poll.getId();
        Queue queue = new Queue(queueName, false);
        amqpAdmin.declareQueue(queue);

        String routingKey = "poll." + poll.getId();
        Binding binding = BindingBuilder.bind(queue).to(topicExch).with(routingKey);
        amqpAdmin.declareBinding(binding);

        System.out.println("Topic created: " + queueName);
    }

    public void subscribeToPollTopic(Poll poll) {
        String queueName = "poll-" + poll.getId();

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(message -> {
            String msq = new String(message.getBody());
            System.out.printf("Vote event received for Poll %d: %s%n", poll.getId(), msq);

            handleVoteEvent(poll, msq);
        });
        container.start();

        System.out.println("Topic subscribed: " + queueName);
    }

    private void handleVoteEvent(Poll poll, String msq) {
        // parse payload and update DB
        // e.g., payload = {"optionId":3}
        // increment vote count for option 3 in poll 42
    }
}
