package com.work;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.util.CollectionUtils;

import java.io.IOException;

public class Consumer2 {

    public static void main(String[] args) throws Exception {
        Connection connection = CollectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(Producer.QUEUE_NAME, true, false, false, null);

        // 每次可以预取多少个消息
        channel.basicQos(1);

        // 1、创建消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    // 路由key
                    System.out.println("路由key：" + envelope.getRoutingKey());
                    // 交换机
                    System.out.println("交换机：" + envelope.getExchange());
                    // 消息ID
                    System.out.println("消息ID：" + envelope.getDeliveryTag());
                    // 接收到的消息
                    System.out.println("消费者2---接收到的消息：" + new String(body, "utf-8"));

                    Thread.sleep(1000);
                    // 确认消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        // 2、监听队列
        channel.basicConsume(Producer.QUEUE_NAME, true, defaultConsumer);

    }

}
