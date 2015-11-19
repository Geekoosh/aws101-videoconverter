package com.geekoosh.aws.video.controllers;

import com.geekoosh.aws.video.services.sqs.SQSAppEvent;
import com.geekoosh.aws.video.services.sqs.SQSAppEventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @RequestMapping("/")
    public String health() {
        return "ok";
    }

    @RequestMapping("/stop/{name}")
    public String stopQueue(@PathVariable(value="name") String queueName) {
        publisher.publishEvent(new SQSAppEvent(SQSAppEventType.STOP, queueName));
        return "ok";
    }

    @RequestMapping("/stop/")
    public String stop() {
        publisher.publishEvent(new SQSAppEvent(SQSAppEventType.STOP));
        return "ok";
    }
}
