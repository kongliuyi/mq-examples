package net.riking.activemq.top;

import net.riking.activemq.config.MqConfig;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopProducter {


	public static void main(String[] args) throws JMSException {
		start();
	}

	static public void start() throws JMSException {
		System.out.println("生产者已经启动....");
		// 创建ActiveMQConnectionFactory 会话工厂
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, MqConfig.BROKERURL);
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();	// 启动JMS 连接
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		MessageProducer producer = session.createProducer(null);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		Destination destination = session.createTopic(MqConfig.TOPIC);
		send(producer, session,destination);
		session.commit();//提交事务
		System.out.println("发送成功!");
		connection.close();
	}

	static public void send(MessageProducer producer, Session session ,Destination destination) throws JMSException {
		for (int i = 1; i <= 5; i++) {
			System.out.println("Top：Hello ActiveMQ！" + i);
			TextMessage textMessage = session.createTextMessage("Top：Hello ActiveMQ！" + i);
			producer.send(destination, textMessage);
		}
	}

}
