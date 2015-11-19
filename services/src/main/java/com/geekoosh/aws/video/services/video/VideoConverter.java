package com.geekoosh.aws.video.services.video;

public interface VideoConverter {
    String generateThumbnail(String path) throws Exception;
    String convertHighResolution(String path) throws Exception;
    String convertLowResolution(String path) throws Exception;
}
