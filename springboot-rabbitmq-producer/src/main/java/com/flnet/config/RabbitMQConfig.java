package com.flnet.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @desc 配置类
 **/
@Configuration
public class RabbitMQConfig {

    /**
     * 交换机名称
     */
    public static final String ITEM_TOPIC_EXCHANGE = "item_topic_exchange";
    /**
     * 队列名称
     */
    public static final String ITEM_QUEUE = "item_queue";

    /**
     * 1、声明交换机
     */
    @Bean("itemTopicExchange")
    public Exchange topicExchange(){
        return ExchangeBuilder.topicExchange(ITEM_TOPIC_EXCHANGE).durable(true).build();
    }
    /**
     * 2、声明队列
     */
    @Bean("itemQueue")
    public Queue itemQueue(){
        return QueueBuilder.durable(ITEM_QUEUE).build();
    }
    /**
     * 3、将队列绑定到交换机
     */
    @Bean
    public Binding itemQueueExchange(@Qualifier("itemQueue") Queue queue, @Qualifier("itemTopicExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("item.#").noargs();
    }

}
