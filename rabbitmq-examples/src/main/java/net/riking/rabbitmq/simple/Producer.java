package net.riking.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import net.riking.rabbitmq.utils.MQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    private static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("简单队列：生产者开启");
        // 1.获取连接
        Connection newConnection = MQConnectionUtils.newConnection();
        // 2.创建通道
        Channel channel = newConnection.createChannel();
        // 3.创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for (int i = 0; i <= 10; i++) {
            String msg = "Hello 简单队列!" + i;
            System.out.println("生产者发送消息:" + msg);
            // 4.发送消息
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }
        channel.close();
        newConnection.close();
    }

}
