package com.example.factureservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String BILLING_EXCHANGE = "billing.topic";
    public static final String ROUTING_PAYMENT_COMPLETED = "payment.completed";
    public static final String ROUTING_INVOICE_OVERDUE = "invoice.overdue";

    public static final String PAYMENT_QUEUE = "payment.queue";
    public static final String INVOICE_OVERDUE_QUEUE = "invoice.overdue.queue";

    @Bean
    public TopicExchange billingExchange() {
        return ExchangeBuilder.topicExchange(BILLING_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Queue paymentQueue() {
        return QueueBuilder.durable(PAYMENT_QUEUE).build();
    }

    @Bean
    public Queue invoiceOverdueQueue() {
        return QueueBuilder.durable(INVOICE_OVERDUE_QUEUE).build();
    }

    @Bean
    public Binding paymentBinding(Queue paymentQueue, TopicExchange billingExchange) {
        return BindingBuilder.bind(paymentQueue)
                .to(billingExchange)
                .with(ROUTING_PAYMENT_COMPLETED);
    }

    @Bean
    public Binding invoiceOverdueBinding(Queue invoiceOverdueQueue, TopicExchange billingExchange) {
        return BindingBuilder.bind(invoiceOverdueQueue)
                .to(billingExchange)
                .with(ROUTING_INVOICE_OVERDUE);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
