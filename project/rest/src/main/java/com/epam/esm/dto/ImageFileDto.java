package com.epam.esm.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageFileDto {
    private MultipartFile file;
    private String name;

    public ImageFileDto() {
    }

    public ImageFileDto(MultipartFile file, String name) {
        this.file = file;
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        ImageFileDto image = (ImageFileDto) obj;
        return (file == null ? file == image.file : file.equals(image.file))
                && (name == null ? name == image.name : name.equals(image.name));
    }

    @Override
    public int hashCode() {
        return (file == null ? 0 : file.hashCode()) + (name == null ? 0 : name.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getName() + "@NAME=" + name;
    }
}
