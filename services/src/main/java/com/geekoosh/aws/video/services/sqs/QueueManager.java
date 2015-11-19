package com.geekoosh.aws.video.services.sqs;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import javax.jms.*;

public abstract class QueueManager implements MessageListener {

    @Autowired
    private SQSConnectionFactory sqsConnectionFactory;

    private SQSConnection connection;
    private Session session;
    private MessageConsumer consumer;
    private Queue queue;

    public String queueName;

    public void stopQueue() throws JMSException {
        connection.close();
        connection = null;
        session.close();
        session = null;
        queue = null;
    }

    public void startQueue() throws JMSException {
        this.connection = sqsConnectionFactory.createConnection();
        this.session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        this.queue = session.createQueue(getQueueName());
        this.consumer = session.createConsumer(queue);
        this.consumer.setMessageListener(this);

        this.connection.start();
    }

    @EventListener
    public void sqsEvent(SQSAppEvent event) throws JMSException {
        if(event.getQueueName() != null && !event.getQueueName().equals(getQueueName())) {
            return;
        }

        if(event.getEventType() == SQSAppEventType.STOP && this.connection != null) {
            stopQueue();
        } else if(event.getEventType() == SQSAppEventType.START && this.connection == null) {
            startQueue();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            handleMessage(message);
            message.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    abstract protected void handleMessage(Message message) throws Exception;
}
