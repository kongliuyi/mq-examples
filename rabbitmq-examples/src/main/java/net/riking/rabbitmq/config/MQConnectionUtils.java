package net.riking.rabbitmq.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQConnectionUtils {


    private   final  static  String  HOST= "127.0.0.1";
    private   final  static  int     PORT=  5672;
    private   final  static String  VIRTUALHOST ="/test001_host";
    private   final  static String   USER_NAME="guest";
    private   final  static String   PASSWORD="guest";

	public static Connection newConnection() throws IOException, TimeoutException {
		// 1.定义连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		// 2.设置服务器地址
		factory.setHost(HOST);
		// 3.设置协议端口号
		factory.setPort(PORT);
		// 4.设置vhost
		factory.setVirtualHost(VIRTUALHOST);
		// 5.设置用户名称
		factory.setUsername(USER_NAME);
		// 6.设置用户密码
		factory.setPassword(PASSWORD);
		// 7.创建新的连接
		Connection newConnection = factory.newConnection();
		return newConnection;
	}

}
