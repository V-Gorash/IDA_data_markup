package com.vgorash.datamarkup.repository;

import com.vgorash.datamarkup.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM ida_image im WHERE im.id NOT IN (SELECT image_id from ida_mapping where user_username = ?1) LIMIT 1")
    Optional<Image> getImagesForUser(String username);
}
