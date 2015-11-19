package com.geekoosh.aws.video.services.file.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "s3")
public class S3Config {
    private String bucket;
    private String workingFolder;

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getWorkingFolder() {
        return workingFolder;
    }

    public void setWorkingFolder(String workingFolder) {
        this.workingFolder = workingFolder;
    }
}
