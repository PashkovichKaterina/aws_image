package com.epam.esm.service.impl;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.epam.esm.entity.Image;
import com.epam.esm.exception.EntityIsAlreadyExistsException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.provider.BucketProvider;
import com.epam.esm.repository.AmazonS3Repository;
import com.epam.esm.repository.ImageRepository;
import com.epam.esm.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageServiceImpl implements ImageService {
    private static final String IMAGE_PNG_CONTENT_TYPE = "image/png";

    private AmazonS3Repository amazonS3Repository;
    private ImageRepository imageRepository;
    private BucketProvider bucketProvider;

    @Autowired
    public ImageServiceImpl(AmazonS3Repository amazonS3Repository, ImageRepository imageRepository,
                            BucketProvider bucketProvider) {
        this.amazonS3Repository = amazonS3Repository;
        this.imageRepository = imageRepository;
        this.bucketProvider = bucketProvider;
    }

    @Override
    public InputStream getByName(String fileName) {
        if (!imageRepository.existsByName(fileName)) {
            throw new NoSuchEntityException("Image with name " + fileName + " is not exist");
        }
        String bucketName = bucketProvider.getImageBucketName();
        String fileKey = imageRepository.findByName(fileName).getId().toString();
        return amazonS3Repository.get(fileKey, bucketName);
    }

    @Override
    public InputStream getRandom() {
        Image image = imageRepository.getRandom();
        if (image == null) {
            throw new NoSuchEntityException("Images not found");
        }
        String bucketName = bucketProvider.getImageBucketName();
        String fileKey = image.getId().toString();
        return amazonS3Repository.get(fileKey, bucketName);
    }

    @Override
    public Image upload(String fileName, MultipartFile file) {
        if (imageRepository.existsByName(fileName)) {
            throw new EntityIsAlreadyExistsException("Image with name " + fileName + " is already exist");
        }
        Image image = new Image(fileName);
        InputStream fileStream;
        ObjectMetadata fileMetadata = new ObjectMetadata();
        try {
            byte[] fileBytes = file.getBytes();
            fileStream = new ByteArrayInputStream(fileBytes);
            fileMetadata.setContentLength(fileBytes.length);
            fileMetadata.setContentType(IMAGE_PNG_CONTENT_TYPE);
        } catch (IOException e) {
            throw new ServiceException("Unavailable to read file " + file.getOriginalFilename());
        }
        imageRepository.save(image);
        String bucketName = bucketProvider.getImageBucketName();
        String fileKey = image.getId().toString();
        amazonS3Repository.upload(fileKey, bucketName, fileStream, fileMetadata);
        return image;
    }
}