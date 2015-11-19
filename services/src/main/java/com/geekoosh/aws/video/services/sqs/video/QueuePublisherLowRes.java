package com.geekoosh.aws.video.services.sqs.video;

import com.geekoosh.aws.video.services.sqs.QueuePublisher;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "lowres")
public class QueuePublisherLowRes extends QueuePublisherConverter {
}
