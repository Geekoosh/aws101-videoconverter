package com.geekoosh.aws.video.services.sqs;


import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.jms.*;

public abstract class QueuePublisher {
    @Autowired
    protected SQSConnectionFactory sqsConnectionFactory;
    protected SQSConnection connection;
    protected Session session;
    protected Queue queue;
    protected MessageProducer producer;

    public String queueName;

    @PostConstruct
    public void init() throws JMSException {
        connection = sqsConnectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue(getQueueName());
        producer = session.createProducer( queue );
    }

    public String publish(String message) throws JMSException {
        TextMessage textMessage = session.createTextMessage(message);
        producer.send(textMessage);
        return textMessage.getJMSMessageID();
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
