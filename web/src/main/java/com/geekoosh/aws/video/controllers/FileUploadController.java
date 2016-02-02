package com.geekoosh.aws.video.controllers;

import com.geekoosh.aws.video.model.ConvertJob;
import com.geekoosh.aws.video.services.file.FileService;
import com.geekoosh.aws.video.services.sqs.video.QueuePublisherHighRes;
import com.geekoosh.aws.video.services.sqs.video.QueuePublisherLowRes;
import com.geekoosh.aws.video.services.sqs.video.QueuePublisherThumbnail;
import com.geekoosh.aws.video.services.sqs.video.VideoConverterMessage;
import com.geekoosh.aws.video.services.video.ControlJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private ControlJobService controlJobService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String home() {
        return "index";
    }

    @RequestMapping(value="/poll/{id}", method= RequestMethod.GET)
    public @ResponseBody ConvertJob poll(@PathVariable("id")Long id) {
        return controlJobService.getById(id);
    }

    @RequestMapping(value="/", method= RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file){
        String baseName = UUID.randomUUID().toString();
        if (!file.isEmpty()) {
            try {

                InputStream strm = file.getInputStream();
                fileService.upload(strm, baseName, true);
                strm.close();

                //Add to db and return token
                ConvertJob convertJob = controlJobService.createConvertJob(baseName);

                //Send to queues
                VideoConverterMessage message = new VideoConverterMessage();
                message.setConvertJobId(convertJob.getId());
                message.setInput(baseName);
                message.setOutput(baseName + ".png");
                message.setPublic(true);
                thumbnail.publish(message);

                message.setOutput(baseName + "_highres.mp4");
                highRes.publish(message);

                message.setOutput(baseName + "_lowres.mp4");
                lowRes.publish(message);

                return convertJob.getId().toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        } else {
            return "failed";
        }
    }
}
