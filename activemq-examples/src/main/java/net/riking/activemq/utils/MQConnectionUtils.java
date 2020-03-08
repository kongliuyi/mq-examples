package net.riking.activemq.utils;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public class MQConnectionUtils {

	private final  static String BROKERURL = "tcp://127.0.0.1:61616";


	public static Connection newConnection() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, BROKERURL);
		// 创建连接
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();
		return connection;
	}

}
