package net.riking.activemq.top;

import net.riking.activemq.config.MqConfig;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopConsumer {


	public static void main(String[] args) throws JMSException {
		start();
	}

	static public void start() throws JMSException {
		System.out.println("消费者启动...");
		// 创建ActiveMQConnectionFactory 会话工厂
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, MqConfig.BROKERURL);
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();// 启动JMS 连接
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic(MqConfig.TOPIC);// 创建一个主题
		MessageConsumer consumer = session.createConsumer(topic);
		// consumer.setMessageListener(new MsgListener());
		while (true) {
			TextMessage textMessage = (TextMessage) consumer.receive();
			if (textMessage != null) {
				System.out.println("接受到消息:" + textMessage.getText());
				// textMessage.acknowledge();// 手动签收
				// session.commit();
			} else {
				break;
			}
		}
		connection.close();
	}

}
