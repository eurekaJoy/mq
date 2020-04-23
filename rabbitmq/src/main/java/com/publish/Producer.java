package com.publish;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.util.CollectionUtils;

/**
 * 发布与订阅模式 - Exchange模式
 * fanout使用广播类型交换机，可以将一个消息发送到绑定了该交换机的队列
 */
public class Producer {

    // 交换机名称
    static final String FANOUT_EXCHANGE = "fanout_exchange";
    // 队列名称
    static final String FANOUT_QUEUE_NAME_1 = "fanout_queue_1";
    static final String FANOUT_QUEUE_NAME_2 = "fanout_queue_2";

    public static void main(String[] args) throws Exception {
        Connection connection = CollectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 1.声明交换机
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        // 2.声明队列
        channel.queueDeclare(FANOUT_QUEUE_NAME_1, true, false, false, null);
        channel.queueDeclare(FANOUT_QUEUE_NAME_2, true, false, false, null);
        // 3.队列绑定到交换机
        channel.queueBind(FANOUT_QUEUE_NAME_1, FANOUT_EXCHANGE, "");
        channel.queueBind(FANOUT_QUEUE_NAME_2, FANOUT_EXCHANGE, "");
        // 消息体
        String message = "test message publish and subscribe";
        for (int i = 1; i <= 10; i++) {
            // 发布消息
            channel.basicPublish(FANOUT_EXCHANGE, "", null, message.getBytes());
            System.out.println("消息已发送：" + message + i);
        }

        // 关闭资源
        channel.close();
        connection.close();
    }


}
