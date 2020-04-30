package com.epam.esm.repository.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.epam.esm.repository.AmazonS3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;

@Repository
@PropertySource("amazon.properties")
public class AmazonS3RepositoryImpl implements AmazonS3Repository {
    @Value("${endpointUrl}")
    private String endpointUrl;

    private AmazonS3 amazonS3Client;

    @Autowired
    public AmazonS3RepositoryImpl(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Override
    public InputStream get(String fileKey, String bucketName) {
        return amazonS3Client.getObject(bucketName, fileKey).getObjectContent();
    }

    @Override
    public void upload(String fileKey, String bucketName, InputStream file, ObjectMetadata metadata) {
        PutObjectRequest objectRequest = new PutObjectRequest(bucketName, fileKey, file, metadata);
        amazonS3Client.putObject(objectRequest);
    }
}