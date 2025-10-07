package com.example.demo.Services;

import com.example.demo.Components.DomainManager;
import com.example.demo.Conf.RabbitConfiguration;
import com.example.demo.Models.Poll;

import com.example.demo.Models.PollTopicVoteModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final DomainManager data;
    private final ObjectMapper mapper;

    public MessageManager(AmqpAdmin _amqpAdmin, TopicExchange _topicExchange, ConnectionFactory _connFac, DomainManager data, ObjectMapper mapper) {
        this.amqpAdmin = _amqpAdmin;
        this.topicExch = _topicExchange;
        this.connFactory = _connFac;
        this.data = data;
        this.mapper = mapper;
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

            try {
                handlePollToppicVoteEvent(poll, msq);
            } catch (JsonProcessingException e) { throw new RuntimeException(e); }
        });
        container.start();

        System.out.println("Topic subscribed: " + queueName);
    }

    public void deletePollTopic(Poll poll) {
        String queueName = "poll-" + poll.getId();
        String routingKey = "poll." + poll.getId();

        amqpAdmin.removeBinding(new Binding(queueName, Binding.DestinationType.QUEUE, RabbitConfiguration.POLL_EXCHANGE, routingKey, null));

        amqpAdmin.deleteQueue(queueName);
    }

    private void handlePollToppicVoteEvent(Poll poll, String msg) throws JsonProcessingException {
        var val = mapper.readValue(msg, PollTopicVoteModel.class);

        switch (val.getType()) {
            case Vote -> {
                data.addVote(val.getUsername(), val.getOptID());
            }
            case UnVote -> {
                data.removeVote(val.getUsername(), val.getOptID());
            }
        }
    }
}
