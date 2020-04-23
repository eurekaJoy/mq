package flnet.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听器
 **/
@Component
public class MyListener {

    /**
     * 使用@RabbitListener接收item_queue队列的消息
     * @param message 接收到的消息体
     */
    @RabbitListener(queues = "item_queue")
    public void myListener(String message){
        System.out.println("消费者消息：" + message);
    }


}
