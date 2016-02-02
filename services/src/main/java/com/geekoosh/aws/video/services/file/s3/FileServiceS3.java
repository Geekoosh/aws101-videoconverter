package com.geekoosh.aws.video.services.file.s3;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.geekoosh.aws.video.services.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@Profile("!local")
public class FileServiceS3 implements FileService {
    @Autowired
    private S3Config s3Config;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Autowired
    private AWSCredentialsProvider credentialsProvider;

    @Override
    public void upload(String path, String dest, boolean isPublic) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(s3Config.getBucket(),
                                                                dest, new File(path));
        upload(putObjectRequest, isPublic);
    }

    @Override
    public void upload(InputStream in, String dest, boolean isPublic) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(s3Config.getBucket(),
                                                                dest, in, new ObjectMetadata());
        upload(putObjectRequest, isPublic);
    }

    private void upload(PutObjectRequest putObjectRequest, boolean isPublic) {
        TransferManager tx = new TransferManager(credentialsProvider);
        if(isPublic) {
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        }
        Upload upload = tx.upload(putObjectRequest);
        System.out.println("uploading " + putObjectRequest.getKey());
        try {
            upload.waitForUploadResult();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void download(String path, String dest) {
        download(path, new File(dest));
    }

    @Override
    public void download(String path, File dest) {
        TransferManager tx = new TransferManager(amazonS3Client);

        System.out.println("downloading from " + s3Config.getBucket() + "/" + path + " to " + dest.getPath());

        Download download = tx.download(s3Config.getBucket(), path, dest);
        try {
            download.waitForCompletion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String downloadTemp(String path) {
        try {
            File temp = File.createTempFile("s3f", null, new File(s3Config.getWorkingFolder()));
            download(path, temp);
            return temp.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
