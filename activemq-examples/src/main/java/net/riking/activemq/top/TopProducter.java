package net.riking.activemq.top;

import net.riking.activemq.utils.MQConnectionUtils;

import javax.jms.*;

public class TopProducter {

    public final static String TOPIC = "my.topic";

    public static void main(String[] args) throws JMSException {
        System.out.println("生产者已经启动....");
        // 创建连接
        Connection connection = MQConnectionUtils.newConnection();
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(null);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        Destination destination = session.createTopic(TOPIC);
        send(producer, session, destination);
        // 提交事务
        session.commit();
        System.out.println("发送成功!");
        connection.close();
    }

    static public void send(MessageProducer producer, Session session, Destination destination) throws JMSException {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Top：Hello ActiveMQ！" + i);
            TextMessage textMessage = session.createTextMessage("Top：Hello ActiveMQ！" + i);
            producer.send(destination, textMessage);
        }
    }

}
