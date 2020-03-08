package net.riking.activemq.p2p;


import net.riking.activemq.utils.MQConnectionUtils;

import javax.jms.*;


public class P2pProducter {

	public final  static String QUEUE = "my.queue";

	public static void main(String[] args) throws JMSException {
		System.out.println("生产者已经启动....");
		/**  Boolean: 是否开启事务    int：
		 * 1.Session.AUTO_ACKNOWLEDGE：自动签收
		 * 2.Session.CLIENT_ACKNOWLEDGE：手动签收
		 * 3.Session.DUPS_ACKNOWLEDGE：该选择只是会话迟钝的确认消息的提交。如果JMS Provider失败，那么可能会导致一些重复的消息。如果是重复的消息，那么JMS Provider必须把消息头的JMSRedelivered字段设置为true。
		 */
		Connection connection = MQConnectionUtils.newConnection();
		// 创建会话工厂
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		// Destination ：消息的目的地;消息发送给谁.
		// 创建 my.queue 队列
		Destination destination = session.createQueue(QUEUE);
		// MessageProducer：消息生产者
		MessageProducer producer = session.createProducer(destination);
		// 设置是否持久化
		// PERSISTENT：持久保存消息
		// NON_PERSISTENT：不持久保存消息。
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		// 发送消息
        sendMsg(session, producer);
		// 提交事务
        session.commit();
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
