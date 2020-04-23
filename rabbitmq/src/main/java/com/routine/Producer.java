package com.routine;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.util.CollectionUtils;

/**
 * 路由模式 - Exchange模式
 * direct使用定向类型交换机，消息携带路由key，交换机根据消息的路由key与队列的路由key比对，一致的话该队列可以接收消息
 */
public class Producer {

    // 交换机名称
    static final String DIRECT_EXCHANGE = "direct_exchange";
    // 队列名称
    static final String DIRECT_QUEUE_INSERT = "direct_queue_insert";
    static final String DIRECT_QUEUE_UPDATE = "direct_queue_update";

    public static void main(String[] args) throws Exception {
        Connection connection = CollectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 1.声明交换机
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 2.声明队列
        channel.queueDeclare(DIRECT_QUEUE_INSERT, true, false, false, null);
        channel.queueDeclare(DIRECT_QUEUE_UPDATE, true, false, false, null);
        // 3.队列绑定到交换机
        channel.queueBind(DIRECT_QUEUE_INSERT, DIRECT_EXCHANGE, "insert");
        channel.queueBind(DIRECT_QUEUE_UPDATE, DIRECT_EXCHANGE, "update");
        // 消息体1
        String message1 = "test message routine pattern key insert";
        // 发布消息1
        channel.basicPublish(DIRECT_EXCHANGE, "insert", null, message1.getBytes());
        System.out.println("消息已发送：" + message1);
        // 消息体2
        String message2 = "test message routine pattern key update";
        // 发布消息2
        channel.basicPublish(DIRECT_EXCHANGE, "update", null, message2.getBytes());
        System.out.println("消息已发送：" + message2);

        // 关闭资源
        channel.close();
        connection.close();
    }


}
