package com.epam.esm.repository;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface AmazonS3Repository {
    InputStream get(String fileKey, String bucketName);

    void upload(String fileKey, String bucketName, InputStream file, ObjectMetadata metadata);
}