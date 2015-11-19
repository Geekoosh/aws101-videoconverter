package com.geekoosh.aws.video.services.file;

import java.io.File;
import java.io.InputStream;

public interface FileService {
    void upload(String path, String dest, boolean isPublic);
    void upload(InputStream in, String dest, boolean isPublic);
    void download(String path, String dest);
    void download(String path, File dest);
    String downloadTemp(String path);
}
