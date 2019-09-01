package net.riking.activemq.p2p;


import net.riking.activemq.config.MqConfig;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class P2pProducter {



	public static void main(String[] args) throws JMSException {
		System.out.println("生产者已经启动....");
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, MqConfig.BROKERURL);
		Connection connection =  connectionFactory.createConnection();//创建连接
		connection.start();//开启连接

		/**  Boolean: 是否开启事务    int：
		 * 1.Session.AUTO_ACKNOWLEDGE：自动签收
		 * 2.Session.CLIENT_ACKNOWLEDGE：手动签收
		 * 3.Session.DUPS_ACKNOWLEDGE：该选择只是会话迟钝的确认消息的提交。如果JMS Provider失败，那么可能会导致一些重复的消息。如果是重复的消息，那么JMS Provider必须把消息头的JMSRedelivered字段设置为true。
		 */
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);// 创建会话工厂
		// Destination ：消息的目的地;消息发送给谁.
		Destination destination = session.createQueue(MqConfig.QUEUE);//创建my-queue队列
		// MessageProducer：消息生产者
		MessageProducer producer = session.createProducer(destination);
		// PERSISTENT：持久保存消息
		// NON_PERSISTENT：不持久保存消息。
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);		// 设置是否持久化
        sendMsg(session, producer);	// 发送消息
        session.commit();//提交事务
		System.out.println("发送成功!");
		connection.close();
	}
	/**
	 * 在指定的会话上，通过指定的消息生产者发出一条消息
	 * 
	 * @param session
	 *            消息会话
	 * @param producer
	 *            消息生产者
	 */
	public static void sendMsg(Session session, MessageProducer producer) throws JMSException {
		for (int i = 1; i <= 5; i++) {
			System.out.println("P2p：Hello ActiveMQ！");
		// 创建一条文本消息
		TextMessage message = session.createTextMessage("P2p：Hello ActiveMQ！" + i);
		// 通过消息生产者发出消息
		producer.send(message);
		}
	}
}
