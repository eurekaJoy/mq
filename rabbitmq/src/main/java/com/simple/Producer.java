package com.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.util.CollectionUtils;

/**
 * 简单模式
 * 一个生产者生产一个消息只能被一个消费者接收
 */
public class Producer {

    static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = CollectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 消息体
        String message = "test message";
        // 发布消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("消息已发送：" + message);
        // 关闭流
        channel.close();
        connection.close();
    }


}
