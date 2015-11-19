package com.geekoosh.aws.video.services.sqs.video;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "thumbnail")
public class QueuePublisherThumbnail extends QueuePublisherConverter {
}
