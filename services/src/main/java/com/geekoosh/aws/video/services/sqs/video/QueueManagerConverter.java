package com.geekoosh.aws.video.services.sqs.video;

import com.geekoosh.aws.video.services.file.FileService;
import com.geekoosh.aws.video.services.sqs.QueueManager;
import com.geekoosh.aws.video.services.video.VideoConverter;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.File;

public abstract class QueueManagerConverter extends QueueManager {
    @Autowired
    protected VideoConverter videoConverter;

    @Autowired
    protected FileService fileService;

    private VideoConverterMessage getVideoConverterMessage(Message message) throws JMSException {
        String messageText = ((TextMessage)message).getText();
        Gson gson = new Gson();
        return gson.fromJson(messageText, VideoConverterMessage.class);
    }

    protected void handleMessage(Message message) throws Exception {
        String input = null;
        String output = null;
        try {
            VideoConverterMessage videoConverterMessage = getVideoConverterMessage(message);
            input = fileService.downloadTemp(videoConverterMessage.getInput());
            output = convert(input);
            fileService.upload(output, videoConverterMessage.getOutput(), videoConverterMessage.isPublic());
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            File inputFile = new File(input);
            if(inputFile.exists()) {
                inputFile.delete();
            }
            File outputFile = new File(output);
            if(outputFile.exists()) {
                outputFile.delete();
            }
        }
    }

    abstract String convert(String input) throws Exception;
}
