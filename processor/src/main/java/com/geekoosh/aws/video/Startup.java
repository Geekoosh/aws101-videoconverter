package com.geekoosh.aws.video;

import com.geekoosh.aws.video.configuration.Queues;
import com.geekoosh.aws.video.services.sqs.QueueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Startup implements CommandLineRunner {
    @Autowired
    private Map<String, QueueManager> queueManagers;

    @Autowired
    private Queues queues;

    @Override
    public void run(String... strings) throws Exception {
        for(String queueName : queues.getNames()) {
            QueueManager queueManager = queueManagers.get(queueName);
            if(queueManager != null) {
                queueManager.startQueue();
            }
        }
    }
}
