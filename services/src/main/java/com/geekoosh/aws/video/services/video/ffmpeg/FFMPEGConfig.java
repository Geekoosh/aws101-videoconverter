package com.geekoosh.aws.video.services.video.ffmpeg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ffmpeg")
public class FFMPEGConfig {
    private String ffmpegPath;
    private String workingFolder;
    private String thumbnailCmd = "${ffmpeg} -i ${input} -ss 00:00:01.000 -vframes 1 ${output}";
    private String lowResCmd = "${ffmpeg} -i ${input} -codec:v libx264 -profile:v high -preset slow -b:v 500k -maxrate 500k -bufsize 1000k -vf scale=-1:240 -threads 0 -codec:a libfdk_aac -b:a 128k ${output}"; //qt-faststart pre_out.mp4 out.mp4
    private String highResCmd = "${ffmpeg} -i ${input} -codec:v libx264 -profile:v high -preset slow -b:v 500k -maxrate 500k -bufsize 1000k -vf scale=-1:480 -threads 0 -codec:a libfdk_aac -b:a 128k ${output}"; //qt-faststart pre_out.mp4 out.mp4

    public String getFfmpegPath() {
        return ffmpegPath;
    }

    public void setFfmpegPath(String ffmpegPath) {
        this.ffmpegPath = ffmpegPath;
    }

    public String getThumbnailCmd() {
        return thumbnailCmd;
    }

    public void setThumbnailCmd(String thumbnailCmd) {
        this.thumbnailCmd = thumbnailCmd;
    }

    public String getLowResCmd() {
        return lowResCmd;
    }

    public void setLowResCmd(String lowResCmd) {
        this.lowResCmd = lowResCmd;
    }

    public String getHighResCmd() {
        return highResCmd;
    }

    public void setHighResCmd(String highResCmd) {
        this.highResCmd = highResCmd;
    }

    public String getWorkingFolder() {
        return workingFolder;
    }

    public void setWorkingFolder(String workingFolder) {
        this.workingFolder = workingFolder;
    }
}
