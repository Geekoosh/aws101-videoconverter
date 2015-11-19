package com.geekoosh.aws.video.services.sqs;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQSService {
    @Autowired
    private AWSCredentialsProvider awsCredentialsProvider;

    /********************/
    /* Queue Management */
    private AmazonSQSClient amazonSQSClient(Regions regionName) {
        AmazonSQSClient amazonSQSClient = new AmazonSQSClient(awsCredentialsProvider);
        Region region = Region.getRegion(regionName);
        amazonSQSClient.setRegion(region);
        return amazonSQSClient;
    }

    public String createQueue(Regions regionName, String name) {
        return createQueue(amazonSQSClient(regionName), name);
    }
    private String createQueue(AmazonSQSClient amazonSQSClient, String name) {
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(name);
        return amazonSQSClient.createQueue(createQueueRequest).getQueueUrl();
    }

    public String ensureQueueExists(Regions regionName, String name) {
        AmazonSQSClient amazonSQSClient = amazonSQSClient(regionName);
        String queueUrl = findQueue(amazonSQSClient, name);
        if(queueUrl != null) {
            queueUrl = createQueue(amazonSQSClient, name);
        }

        return queueUrl;
    }

    public List<String> listQueues(Regions regionName) {
        return listQueues(amazonSQSClient(regionName));
    }
    private List<String> listQueues(AmazonSQSClient amazonSQSClient) {
        return amazonSQSClient.listQueues().getQueueUrls();
    }

    public List<String> findQueues(Regions regionName, String namePrefix) {
        return findQueues(amazonSQSClient(regionName), namePrefix);
    }
    private List<String> findQueues(AmazonSQSClient amazonSQSClient, String namePrefix) {
        return amazonSQSClient.listQueues(namePrefix).getQueueUrls();
    }

    public String findQueue(Regions regionName, String name) {
        return findQueue(amazonSQSClient(regionName), name);
    }
    private String findQueue(AmazonSQSClient amazonSQSClient, String name) {
        ListQueuesResult result = amazonSQSClient.listQueues(name);
        return amazonSQSClient.getQueueUrl(name).getQueueUrl();
    }

    public void deleteQueue(Regions regionName, String name) {
        AmazonSQSClient amazonSQSClient = amazonSQSClient(regionName);
        deleteQueue(amazonSQSClient, findQueue(amazonSQSClient, name));
    }
    private void deleteQueue(AmazonSQSClient amazonSQSClient, String queueUrl) {
        amazonSQSClient.deleteQueue(new DeleteQueueRequest(queueUrl));
    }

    /********************/
    /* Post Messages */
    public SendMessageResult sendMessageByQueueName(Regions regionName, String name, String message) {
        AmazonSQSClient amazonSQSClient = amazonSQSClient(regionName);
        return sendMessageByQueueUrl(amazonSQSClient, findQueue(amazonSQSClient, name), message);
    }
    private SendMessageResult sendMessageByQueueUrl(AmazonSQSClient amazonSQSClient, String queueUrl, String message) {
        return amazonSQSClient.sendMessage(new SendMessageRequest(queueUrl, message));
    }
    public SendMessageResult sendMessageByQueueUrl(Regions regionName, String queueUrl, String message) {
        return sendMessageByQueueUrl(amazonSQSClient(regionName), queueUrl, message);
    }

    /********************/
    /* Peek Messages */
    public List<Message> peekMessagesByQueueName(Regions regionName, String name) {
        AmazonSQSClient amazonSQSClient = amazonSQSClient(regionName);
        return peekMessagesByQueueUrl(amazonSQSClient, findQueue(amazonSQSClient, name), 0, 10);
    }
    public List<Message> peekMessagesByQueueName(Regions regionName, String name, Integer waitTime, Integer maxMessages) {
        AmazonSQSClient amazonSQSClient = amazonSQSClient(regionName);
        return peekMessagesByQueueUrl(amazonSQSClient, findQueue(amazonSQSClient, name), waitTime, maxMessages);
    }
    public List<Message> peekMessagesByQueueUrl(Regions regionName, String queueUrl) {
        return peekMessagesByQueueUrl(amazonSQSClient(regionName), queueUrl, 0, 10);
    }
    public List<Message> peekMessagesByQueueUrl(Regions regionName, String queueUrl, Integer waitTime, Integer maxMessages) {
        return peekMessagesByQueueUrl(amazonSQSClient(regionName), queueUrl, waitTime, maxMessages);
    }
    private List<Message> peekMessagesByQueueUrl(AmazonSQSClient amazonSQSClient, String queueUrl, Integer waitTime, Integer maxMessages) {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl)
                                                        .withWaitTimeSeconds(waitTime)
                                                        .withMaxNumberOfMessages(maxMessages);
        return amazonSQSClient.receiveMessage(receiveMessageRequest).getMessages();
    }

    /********************/
    /* Delete Message */
    public void deleteMessageByQueueName(Regions regionName, String name, Message message) {
        AmazonSQSClient amazonSQSClient = amazonSQSClient(regionName);
        deleteMessage(amazonSQSClient, findQueue(amazonSQSClient, name), message);
    }
    public void deleteMessageByQueueUrl(Regions regionName, String queueUrl, Message message) {
        deleteMessage(amazonSQSClient(regionName), queueUrl, message);
    }
    private void deleteMessage(AmazonSQSClient amazonSQSClient, String queueUrl, Message message) {
        amazonSQSClient.deleteMessage(queueUrl, message.getReceiptHandle());
    }
}
