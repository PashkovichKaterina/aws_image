package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String name;

    public ImageDto() {
    }

    public ImageDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
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
        ImageDto image = (ImageDto) obj;
        return (id == null ? id == image.id : id.equals(image.id))
                && (name == null ? name == image.name : name.equals(image.name));
    }

    @Override
    public int hashCode() {
        return (id == null ? 0 : id.hashCode()) + (name == null ? 0 : name.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getName() + "@ID=" + id + "; NAME=" + name;
    }
}
