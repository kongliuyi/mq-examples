package net.riking.rabbitmq.simple;

import com.rabbitmq.client.*;
import net.riking.rabbitmq.config.MQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer {

	private static final String QUEUE_NAME = "simple_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		System.out.println("简单队列：消费者开启");
		// 1.获取连接
		Connection newConnection = MQConnectionUtils.newConnection();
		// 2.获取通道
		Channel channel = newConnection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String msgString = new String(body, "UTF-8");
				System.out.println("消费者获取消息:" + msgString);
			}
		};
		// 3.监听队列
		channel.basicConsume(QUEUE_NAME, true, defaultConsumer);

	}

}
