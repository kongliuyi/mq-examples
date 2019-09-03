package net.riking.activemq.top;

import net.riking.activemq.config.MQConnectionUtils;

import javax.jms.*;

public class TopConsumer {

	public final  static String TOPIC = "my.topic";

	public static void main(String[] args) throws JMSException {
		System.out.println("消费者启动...");
		Connection connection = MQConnectionUtils.newConnection();// 创建连接
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic(TOPIC);// 创建一个主题
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
