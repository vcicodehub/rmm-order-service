package com.signet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.signet.messaging.QueueMessageReceiver;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

//@Configuration
public class RabbitMQConfig {
    public static final String topicExchangeName = "spring-boot-exchange-topic";
    public static final String fanoutExchangeName = "spring-boot-exchange-fanout";

    public static final String queueName = "spring-boot";
    public static final String fanoutQueueName = "spring-boot-fanout";
    public static final String topicQueueName = "spring-boot-exchange";
  
    // Topic Queue
    @Bean
    Queue topicQueue() {
      return new Queue(topicQueueName, false);
    }

    @Bean
    TopicExchange topicExchange() {
      return new TopicExchange(topicExchangeName);
    }
  
    @Bean
    Binding topicBinding(Queue topicQueue, TopicExchange topicExchange) {
      return BindingBuilder.bind(topicQueue).to(topicExchange).with("foo.bar.#");
    }

    // Fanout Queue
    @Bean
    Queue fanoutQueue() {
      return new Queue(fanoutQueueName, false);
    }

    @Bean
    FanoutExchange fanoutExchange() {
      return new FanoutExchange(fanoutExchangeName);
    }
  
    @Bean
    Binding fanoutBinding(Queue fanoutQueue, FanoutExchange fanoutExchange) {
      return BindingBuilder.bind(fanoutQueue).to(fanoutExchange);
    }
  
    @Bean
    Queue queue() {
      //return new Queue(queueName, false);
      return new AnonymousQueue();
    }
  
    @Bean
    Binding binding(Queue queue, FanoutExchange fanoutExchange) {
      return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
      SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
      container.setConnectionFactory(connectionFactory);
      container.setQueueNames(fanoutQueueName, queue().getName());
      container.setMessageListener(listenerAdapter);
      return container;
    }
  
    @Bean
    MessageListenerAdapter listenerAdapter(QueueMessageReceiver receiver) {
      return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
