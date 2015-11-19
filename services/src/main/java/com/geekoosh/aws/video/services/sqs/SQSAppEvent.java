package com.geekoosh.aws.video.services.sqs;


public class SQSAppEvent {
    private SQSAppEventType eventType;
    private String queueName;

    public SQSAppEvent(SQSAppEventType eventType, String queueName) {
        this.eventType = eventType;
        this.queueName = queueName;
    }

    public SQSAppEvent(SQSAppEventType eventType) {
        this.eventType = eventType;
    }

    public SQSAppEventType getEventType() {
        return eventType;
    }

    public void setEventType(SQSAppEventType eventType) {
        this.eventType = eventType;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
