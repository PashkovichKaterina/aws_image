package com.epam.esm.converter;

import com.epam.esm.dto.ImageDto;
import com.epam.esm.entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {
    public ImageDto toImageDto(Image image) {
        ImageDto imageDto = null;
        if (image != null) {
            imageDto = new ImageDto();
            imageDto.setId(image.getId());
            imageDto.setName(image.getName());
        }
        return imageDto;
    }
}
