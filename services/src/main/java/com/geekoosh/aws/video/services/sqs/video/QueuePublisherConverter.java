package com.geekoosh.aws.video.services.sqs.video;

import com.geekoosh.aws.video.services.sqs.QueuePublisher;
import com.google.gson.Gson;

import javax.jms.JMSException;

public abstract class QueuePublisherConverter extends QueuePublisher {
    public String publish(VideoConverterMessage message) throws JMSException {
        Gson gson = new Gson();
        String textMessage = gson.toJson(message);

        return publish(textMessage);
    }
}
