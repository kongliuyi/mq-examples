package net.riking.activemq.p2p;


import net.riking.activemq.utils.MQConnectionUtils;

import javax.jms.*;

public class P2pConsumer {

    public final static String QUEUE = "my.queue";

    public static void main(String[] args) throws JMSException {
        // 创建连接
        Connection connection = MQConnectionUtils.newConnection();
        // 一个发送或接收消息的线程
        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        // Destination ：消息的目的地;消息发送给谁.
        // 获取 session 注意参数值 my.queue 是一个服务器的 queue，须在在 ActiveMq 的 console 配置
        Destination destination = session.createQueue(QUEUE);
        // 消费者，消息接收者
        MessageConsumer consumer = session.createConsumer(destination);
        while (true) {
            TextMessage message = (TextMessage) consumer.receive();
            if (null != message) {
                System.out.println("收到消息：" + message.getText());
                message.acknowledge();// 手动签收
            } else {
                break;
            }

        }
        session.close();
        connection.close();
    }
}
