package com.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.util.CollectionUtils;

/**
 * 通配符模式 - Exchange模式
 * topic使用通配符类型交换机，消息携带路由key（*表示一个单词、#表示多个单词），
 * 交换机将消息的路由key与队列的路由key比对，匹配的话，该队列可以接收消息
 */
public class Producer {

    // 交换机名称
    static final String TOPIC_EXCHANGE = "topic_exchange";
    // 队列名称
    static final String TOPIC_QUEUE_1 = "topic_queue_1";
    static final String TOPIC_QUEUE_2 = "topic_queue_2";

    public static void main(String[] args) throws Exception {
        Connection connection = CollectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 1.声明交换机
        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);

        /*// 2.声明队列
        channel.queueDeclare(TOPIC_QUEUE_1, true, false, false, null);
        channel.queueDeclare(TOPIC_QUEUE_2, true, false, false, null);
        // 3.队列绑定到交换机
        channel.queueBind(TOPIC_QUEUE_1, TOPIC_EXCHANGE, "item.insert");
        channel.queueBind(TOPIC_QUEUE_2, TOPIC_EXCHANGE, "item.update");*/

        // 消息体1
        String message1 = "test message topic; Routine key item.insert";
        // 发布消息1
        channel.basicPublish(TOPIC_EXCHANGE, "item.insert", null, message1.getBytes());
        System.out.println("消息已发送：" + message1);

        // 消息体2
        String message2 = "test message topic; Routine key item.update";
        // 发布消息2
        channel.basicPublish(TOPIC_EXCHANGE, "item.update", null, message2.getBytes());
        System.out.println("消息已发送：" + message2);

        // 消息体3
        String message3 = "test message topic; Routine key item.delete";
        // 发布消息3
        channel.basicPublish(TOPIC_EXCHANGE, "item.delete", null, message3.getBytes());
        System.out.println("消息已发送：" + message3);

        // 关闭资源
        channel.close();
        connection.close();
    }


}
