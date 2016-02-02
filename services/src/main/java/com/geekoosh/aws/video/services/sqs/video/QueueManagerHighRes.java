package com.geekoosh.aws.video.services.sqs.video;

import com.geekoosh.aws.video.services.video.ControlJobService;
import com.geekoosh.aws.video.services.video.VideoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component(value="highres")
@ConfigurationProperties(prefix = "highres")
public class QueueManagerHighRes extends QueueManagerConverter {
    @Autowired
    private VideoConverter videoConverter;

    @Autowired
    private ControlJobService controlJobService;

    @Override
    String convert(String input) throws Exception {
        return videoConverter.convertHighResolution(input);
    }

    void ack(VideoConverterMessage msg) throws Exception {
        controlJobService.updateConvertJobHighRes(msg.getConvertJobId(), msg.getOutput());
    }
}
