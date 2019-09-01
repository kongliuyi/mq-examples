package net.riking.activemq.p2p;


import net.riking.activemq.config.MqConfig;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class P2pConsumer {



	public static void main(String[] args) throws JMSException {
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, MqConfig.BROKERURL);
		Connection connection = connectionFactory.createConnection();// 创建连接
		connection.start();// 开启连接
		Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);// 一个发送或接收消息的线程
		// Destination ：消息的目的地;消息发送给谁.
		// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
		Destination destination = session.createQueue(MqConfig.QUEUE);
		// 消费者，消息接收者
		MessageConsumer consumer = session.createConsumer(destination);
		while (true) {
			TextMessage message = (TextMessage) consumer.receive();
			if (null != message) {
				System.out.println("收到消息：" + message.getText());
				message.acknowledge();// 手动签收
			} else
				break;
		}
		session.close();
		connection.close();
	}
}
