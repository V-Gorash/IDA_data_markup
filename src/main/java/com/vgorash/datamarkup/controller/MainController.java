package com.vgorash.datamarkup.controller;

import com.vgorash.datamarkup.model.User;
import com.vgorash.datamarkup.service.ImageService;
import com.vgorash.datamarkup.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.Objects;

@Controller
public class MainController {

    private final UserService userService;
    private final ImageService imageService;

    public MainController(UserService userService,
                          ImageService imageService){
        this.userService = userService;
        this.imageService = imageService;
    }

    @RequestMapping( "/")
    public RedirectView main(Map<String, Object> model){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(userId);
        if(user.isDeveloper()){
            return new RedirectView("/developer");
        }
        if(user.isAccessor()){
            return new RedirectView("/mapping");
        }
        return new RedirectView("/error");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/mapping")
    public String mapping(Map<String, Object> model){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(userId);
        ImageService.ImageContainer container = imageService.getImageForUser(user);
        if(Objects.isNull(container)){
            return "allmapped";
        }
        model.put("base64Image", container.getImageBase64());
        model.put("imageId", container.getId());
        return "mapping";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/mapping/{id}")
    public RedirectView mapping(@PathVariable Long id, @RequestParam(required = true) boolean result){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(userId);
        imageService.createMapping(id, user, result);
        return new RedirectView("/mapping");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/developer")
    public String developer(){
        return "developer";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/report", produces = "text/csv")
    public @ResponseBody String report(@RequestParam int numMarks){
        return imageService.createReport(numMarks);
    }

}
