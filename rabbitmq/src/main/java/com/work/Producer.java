package com.work;

import com.util.CollectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 工作队列模式
 * 生产者发送一个消息到一个队列中，然后可以被多个消费者监听到该队列；
 * 一个消息只能被一个队列接收，队列之间存在竞争关系
 */
public class Producer {

    static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = CollectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        for (int i = 0; i <= 30 ; i++) {
            // 消息体
            String message = "test message，work模式---" + i;
            // 发布消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("消息已发送：" + message);
        }
        // 关闭流
        channel.close();
        connection.close();
    }


}
