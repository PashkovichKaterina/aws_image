package com.epam.esm.service;

import com.epam.esm.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface ImageService {
    InputStream getByName(String fileName);

    InputStream getRandom();

    Image upload(String fileName, MultipartFile file);
}
