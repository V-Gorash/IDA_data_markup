package com.vgorash.datamarkup.service;

import com.vgorash.datamarkup.model.Image;
import com.vgorash.datamarkup.model.Mapping;
import com.vgorash.datamarkup.model.User;
import com.vgorash.datamarkup.repository.ImageRepository;
import com.vgorash.datamarkup.repository.MappingRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Setter
    @Getter
    public static class ImageContainer{
        private Long id;
        private String imageBase64;
    }

    @Value("${ida.basedir}")
    private String basePath;

    private final ImageRepository imageRepository;
    private final MappingRepository mappingRepository;

    public ImageService(ImageRepository imageRepository,
                        MappingRepository mappingRepository){
        this.imageRepository = imageRepository;
        this.mappingRepository = mappingRepository;
    }

    public ImageContainer getImageForUser(User user){
        List<Image> images = imageRepository.getImagesForUser(user.getUsername());
        if(images.isEmpty()){
            return null;
        }
        try{
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Image image = images.get(0);
            BufferedImage bufferedImage = ImageIO.read(new File(basePath + "/" + image.getFilepath()));
            ImageIO.write(bufferedImage, "png", output);
            ImageContainer container = new ImageContainer();
            container.setImageBase64(Base64.getEncoder().encodeToString(output.toByteArray()));
            container.setId(image.getId());
            return container;
        }
        catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public void createMapping(Long imageId, User user, boolean result){
        Optional<Image> imageOptional = imageRepository.findById(imageId);
        if(imageOptional.isEmpty()){
            throw new RuntimeException("Incorrect image ID!");
        }
        Mapping mapping = new Mapping();
        mapping.setImage(imageOptional.get());
        mapping.setUser(user);
        mapping.setResult(result);

        mappingRepository.saveAndFlush(mapping);
    }
}
