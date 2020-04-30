package com.epam.esm.repository;

import com.epam.esm.entity.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    @Query("select image from Image image where upper(image.name) = upper(:name)")
    Image findByName(@Param("name") String name);

    @Query("select case when count(image) > 0 then true else false end from Image image where upper(image.name) = upper(:name)")
    boolean existsByName(@Param("name") String name);

    @Query(value = "select * from images order by random() limit 1", nativeQuery = true)
    Image getRandom();
}