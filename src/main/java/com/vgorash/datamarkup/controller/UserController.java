package com.vgorash.datamarkup.controller;

import com.vgorash.datamarkup.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/registration")
    public String registerPage(Map<String, Object> model,
                               @RequestParam(required = false) String passwordError,
                               @RequestParam(required = false) String loginError){
        model.put("passwordError", passwordError);
        model.put("loginError", loginError);
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/registration")
    public RedirectView doRegister(RedirectAttributes attributes,
                                   @RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String repeatPassword){
        if(!password.equals(repeatPassword)){
            attributes.addAttribute("passwordError", "");
            return new RedirectView("/registration");
        }
        if(userService.addUser(username, password)){
            return new RedirectView("/");
        }
        attributes.addAttribute("loginError", "");
        return new RedirectView("/registration");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/auth")
    public String loginPage(Map<String, Object> model, @RequestParam(required = false) String error){
        model.put("error", error);
        return "auth";
    }

}
