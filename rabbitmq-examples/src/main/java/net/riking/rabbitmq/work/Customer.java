package net.riking.rabbitmq.work;

import com.rabbitmq.client.*;
import net.riking.rabbitmq.config.MQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer {
	private static final String QUEUE_NAME = "test_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		System.out.println("001");
		// 1.获取连接
		Connection newConnection = MQConnectionUtils.newConnection();
		// 2.获取通道
		final Channel channel = newConnection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.basicQos(1);// 保证一次只分发一次 限制发送给同一个消费者 不得超过一条消息
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String msgString = new String(body, "UTF-8");
				System.out.println("消费者获取消息:" + msgString);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {

				} finally {
					// 手动回执消息
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		// 3.监听队列
		channel.basicConsume(QUEUE_NAME, false, defaultConsumer);

	}

}
