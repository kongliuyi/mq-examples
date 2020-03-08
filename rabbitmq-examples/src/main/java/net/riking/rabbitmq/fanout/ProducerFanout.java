package net.riking.rabbitmq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import net.riking.rabbitmq.utils.MQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerFanout {

    private static final String EXCHANGE_NAME = "fanout_exchange";


    /**
     * 注意：如果消费没有绑定交换机和队列，则消息会丢失,因为交换机不能存储消息
     * @param args
     * @throws IOException
     * @throws TimeoutException
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("发布订阅模式：生产者启动");
        // 1.创建新的连接
        Connection connection = MQConnectionUtils.newConnection();
        // 2.创建通道
        Channel channel = connection.createChannel();
        // 3.绑定的交换机 参数1:交互机名称. 参数2: exchange类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String msg = ":Hello 发布订阅模式!";
        System.out.println(msg);
        // 4.发送消息
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

        // 5.关闭通道、连接
        channel.close();
        connection.close();


    }

}
