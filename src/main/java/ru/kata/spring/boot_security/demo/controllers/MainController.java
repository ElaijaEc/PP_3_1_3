package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/index"})
    public String getIndexPage() {
        return "index";
    }

    @GetMapping(value = "/user")
    public String getUserInfo(ModelMap modelMap) {
        User userDetails = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<String> roles = new ArrayList<>();
        for(Role role : userDetails.getRoles()){
            roles.add(role.getRole());
        }
        modelMap.addAttribute("userroles",roles);
        modelMap.addAttribute("userinfo",userDetails);
        return "user";
    }
}
