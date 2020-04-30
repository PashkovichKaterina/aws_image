package com.epam.esm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "varchar(1000)")
    private String name;

    public Image() {
    }

    public Image(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Image(String name) {
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
        Image image = (Image) obj;
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