package com.geekoosh.aws.video.configuration;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.geekoosh.aws.video.services.sqs.SQSConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Connection;

@Configuration
public class AWSConfiguration {

    @Autowired
    private SQSConfig sqsConfig;

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new DefaultAWSCredentialsProviderChain();
    }

    @Bean
    public AmazonS3Client amazonS3Client(AWSCredentialsProvider awsCredentialsProvider) {
        AmazonS3Client client = new AmazonS3Client(awsCredentialsProvider);
        client.setRegion(Region.getRegion(Regions.US_EAST_1));
        return client;
    }

    @Bean
    public AmazonSQSClient amazonSQSClient(AWSCredentialsProvider awsCredentialsProvider) {
        return new AmazonSQSClient(awsCredentialsProvider);
    }

    @Bean
    public SQSConnectionFactory.Builder connectionFactoryBuilder(AWSCredentialsProvider awsCredentialsProvider) {
        return new SQSConnectionFactory.Builder()
                .withAWSCredentialsProvider(awsCredentialsProvider)
                .withRegionName(sqsConfig.getRegionName())
                .withNumberOfMessagesToPrefetch(sqsConfig.getPrefetchMessages());
    }

    @Bean
    public SQSConnectionFactory connectionFactory(SQSConnectionFactory.Builder connectionFactoryBuilder) {
        return connectionFactoryBuilder.build();
    }

    @Bean(initMethod = "start", destroyMethod = "close")
    public Connection Connection(SQSConnectionFactory connectionFactory) throws Exception {
        return connectionFactory.createConnection();
    }
}
