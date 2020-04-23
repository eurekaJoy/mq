package com.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author EurekaJoy
 * @desc Obtain Connection facilities class
 * @create 2020-04-22 13:20
 **/
public class CollectionUtils {

    public static Connection getConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/virtual_alvin");
        factory.setUsername("alvin");
        factory.setPassword("alvin");
        return factory.newConnection();
    }

}
