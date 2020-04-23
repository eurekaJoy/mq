package com.routine;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.util.CollectionUtils;

import java.io.IOException;

public class Consumer2 {

    public static void main(String[] args) throws Exception {
        Connection connection = CollectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 1.声明交换机
        channel.exchangeDeclare(Producer.DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 2.声明队列
        channel.queueDeclare(Producer.DIRECT_QUEUE_UPDATE, true, false, false, null);
        // 3.队列绑定到交换机
        channel.queueBind(Producer.DIRECT_QUEUE_UPDATE, Producer.DIRECT_EXCHANGE, "update");

        // 创建消费者
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
                System.out.println("消费者 2 ---接收到的消息：" + new String(body, "utf-8"));
            }
        };
        // 2、监听队列
        channel.basicConsume(Producer.DIRECT_QUEUE_UPDATE, true, defaultConsumer);

    }

}
