package com.geekoosh.aws.video.services.sqs.video;

import com.geekoosh.aws.video.services.video.VideoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component(value = "lowres")
@ConfigurationProperties(prefix = "lowres")
public class QueueManagerLowRes extends QueueManagerConverter {
    @Autowired
    private VideoConverter videoConverter;

    @Override
    String convert(String input) throws Exception {
        return videoConverter.convertLowResolution(input);
    }
}
