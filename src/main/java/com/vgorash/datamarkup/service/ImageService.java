package com.vgorash.datamarkup.service;

import com.vgorash.datamarkup.model.Image;
import com.vgorash.datamarkup.model.Mapping;
import com.vgorash.datamarkup.model.User;
import com.vgorash.datamarkup.repository.ImageRepository;
import com.vgorash.datamarkup.repository.MappingRepository;
import com.vgorash.datamarkup.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
    private final UserRepository userRepository;

    public ImageService(ImageRepository imageRepository,
                        MappingRepository mappingRepository,
                        UserRepository userRepository){
        this.imageRepository = imageRepository;
        this.mappingRepository = mappingRepository;
        this.userRepository = userRepository;
    }

    public ImageContainer getImageForUser(User user){
        Optional<Image> imageOptional = imageRepository.getImagesForUser(user.getUsername());
        if(imageOptional.isEmpty()){
            return null;
        }
        try{
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Image image = imageOptional.get();
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
        if(!Objects.isNull(imageOptional.get().getGt())){
            if(!imageOptional.get().getGt().equals(result)){
                user.setNumFails(user.getNumFails() + 1);
            }
        }
        Mapping mapping = new Mapping();
        mapping.setImage(imageOptional.get());
        mapping.setUser(user);
        mapping.setResult(result);

        mappingRepository.saveAndFlush(mapping);
    }

    public String createReport(int marksCount){
        if(marksCount == 0){
            throw new RuntimeException("zero marks count");
        }
        if(marksCount % 2 == 0){
            marksCount--;
        }
        StringBuilder result = new StringBuilder();
        List<Image> images = imageRepository.findAll();
        for (Image image : images){
            if(image.getMappings().size() >= marksCount && Objects.isNull(image.getGt())){
                int marksPositive = 0;
                int marksNegative = 0;
                List<Mapping> mappings = image.getMappings().stream().sorted(Comparator.comparingInt(a -> a.getUser().getNumFails())).toList();
                for(int i=0; i<marksCount; i++){
                    if(mappings.get(i).isResult()) marksPositive++; else marksNegative++;
                }
                int mark = marksPositive > marksNegative ? 1:0;
                result
                        .append(image.getId()).append(",")
                        .append(image.getFilepath()).append(",")
                        .append(mark)
                        .append("\n");
            }
        }
        return result.toString();
    }
}
