package com.geekoosh.aws.video.controllers;

import com.geekoosh.aws.video.services.file.FileService;
import com.geekoosh.aws.video.services.sqs.video.QueuePublisherHighRes;
import com.geekoosh.aws.video.services.sqs.video.QueuePublisherLowRes;
import com.geekoosh.aws.video.services.sqs.video.QueuePublisherThumbnail;
import com.geekoosh.aws.video.services.sqs.video.VideoConverterMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Controller
public class FileUploadController {
    @Autowired
    private FileService fileService;

    @Autowired
    private QueuePublisherHighRes highRes;

    @Autowired
    private QueuePublisherLowRes lowRes;

    @Autowired
    private QueuePublisherThumbnail thumbnail;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String home() {
        return "index";
    }

    @RequestMapping(value="/", method= RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file){
        String baseName = UUID.randomUUID().toString();
        if (!file.isEmpty()) {
            try {

                InputStream strm = file.getInputStream();
                fileService.upload(strm, baseName, true);
                strm.close();

                //TODO: Add to db and return token

                //TODO: Send to queues
                VideoConverterMessage message = new VideoConverterMessage();
                message.setInput(baseName);
                message.setOutput(baseName + ".png");
                message.setPublic(true);
                thumbnail.publish(message);

                message.setOutput(baseName + "_highres.mp4");
                highRes.publish(message);

                message.setOutput(baseName + "_lowres.mp4");
                lowRes.publish(message);

                return baseName;
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        } else {
            return "failed";
        }
    }
}
