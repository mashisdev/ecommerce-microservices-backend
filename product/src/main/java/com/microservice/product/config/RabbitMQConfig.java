package com.microservice.product.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queues.inventory}")
    private String inventoryQueueName;
    @Value("${spring.rabbitmq.queues.order.response}")
    private String orderResponseQueue;
    @Value("${spring.rabbitmq.queues.order.request}")
    private String orderRequestQueue;

    @Value("${spring.rabbitmq.keys.inventory}")
    private String inventoryKey;
    @Value("${spring.rabbitmq.keys.order}")
    private String orderKey;

    @Value("${spring.rabbitmq.topic.exchange}")
    private String exchange;

    // Queues
    @Bean("inventoryUpdateQueue")
    public Queue inventoryUpdateQueue() {
        return new Queue(inventoryQueueName, true);
    }
    @Bean("orderResponseQueue")
    public Queue orderResponseQueue() {
        return new Queue(orderResponseQueue, true);
    }
    @Bean("orderRequestQueue")
    public Queue orderRequestQueue() {
        return new Queue(orderRequestQueue, true);
    }

    // Exchange
    @Bean
    public TopicExchange productExchange() {
        return new TopicExchange(exchange);
    }

    // Bindings
    @Bean
    public Binding inventoryBinding(@Qualifier("inventoryUpdateQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(inventoryKey);
    }

    @Bean
    public Binding orderResponseBinding(@Qualifier("orderResponseQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(orderKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}