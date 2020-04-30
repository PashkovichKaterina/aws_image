package com.epam.esm.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("bucket.properties")
public class BucketProvider {
    @Value("${imageBucketName}")
    private String imageBucketName;

    public String getImageBucketName() {
        return imageBucketName;
    }
}