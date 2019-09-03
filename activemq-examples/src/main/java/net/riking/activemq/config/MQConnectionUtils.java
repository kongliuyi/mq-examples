package net.riking.activemq.config;

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
		Connection connection = connectionFactory.createConnection();// 创建连接
		connection.start();//开启连接
		return connection;
	}

}
