package com.simple;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.util.CollectionUtils;

import java.io.IOException;

public class Consumer {

    /**
     * 消费者需要持续监听队列，因此不需要关闭资源
     */
    public static void main(String[] args) throws Exception {
        Connection connection = CollectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(Producer.QUEUE_NAME, true, false, false, null);
        // 1、创建消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 路由key
                System.out.println("路由key：" + envelope.getRoutingKey());
                // 交换机
                System.out.println("交换机：" + envelope.getExchange());
                // 消息ID
                System.out.println("消息ID：" + envelope.getDeliveryTag());
                // 接收到的消息
                System.out.println("接收到的消息：" + new String(body, "utf-8"));
            }
        };
        // 2、监听队列
        channel.basicConsume(Producer.QUEUE_NAME, true, defaultConsumer);

    }

}
