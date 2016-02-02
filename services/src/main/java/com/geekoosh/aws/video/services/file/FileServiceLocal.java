package com.geekoosh.aws.video.services.file;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Service
@Profile("local")
public class FileServiceLocal implements FileService{
    @Override
    public void upload(String path, String dest, boolean isPublic) {

    }

    @Override
    public void upload(InputStream in, String dest, boolean isPublic) {

    }

    @Override
    public void download(String path, String dest) {

    }

    @Override
    public void download(String path, File dest) {

    }

    @Override
    public String downloadTemp(String path) {
        return null;
    }
}
