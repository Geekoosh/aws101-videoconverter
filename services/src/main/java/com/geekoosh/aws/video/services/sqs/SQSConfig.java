package com.geekoosh.aws.video.services.sqs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix="sqs")
public class SQSConfig {
    private String regionName;
    private int prefetchMessages = 1;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getPrefetchMessages() {
        return prefetchMessages;
    }

    public void setPrefetchMessages(int prefetchMessages) {
        this.prefetchMessages = prefetchMessages;
    }
}
