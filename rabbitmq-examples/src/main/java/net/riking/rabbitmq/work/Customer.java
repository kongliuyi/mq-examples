package net.riking.rabbitmq.work;

import com.rabbitmq.client.*;
import net.riking.rabbitmq.config.MQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer {
	private static final String QUEUE_NAME = "work_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		System.out.println("工作队列：消费者开启");
		// 1.获取连接
		Connection newConnection = MQConnectionUtils.newConnection();
		// 2.获取通道
		final Channel channel = newConnection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//不要在同一时间给一个消费者超过一条消息
		channel.basicQos(1);// 公平转发，保证应答模式之前只存在一条消息
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String msgString = new String(body, "UTF-8");
				System.out.println("消费者获取消息:" + msgString);
				try {
					Thread.sleep(500);  //验证工作队列，只需修改休眠时间然后再次启动
				} catch (Exception e) {

				} finally {
					// 手动应答回执消息
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		channel.basicConsume(QUEUE_NAME, false, defaultConsumer);// 3.监听队列

	}

}
