package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin") // указываем для адм юрл чтоб не писать постоянно /admin/delete
public class AdminController {
    private UserService userService;

    @Autowired
    AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping //
    public String allUsersList(ModelMap model) {
        List<User> userList = userService.getUserList();
        model.addAttribute("users", userList);
        model.addAttribute("newuser", new User());
        return "allusers";
    }

    @GetMapping(value = "/delete")
    public String deleteUser(@RequestParam(value = "id") long id, Model model) {
        userService.removeUser(id);
        return "userdelete";
    }

    @GetMapping(value = "/add")
    public String saveUser(@ModelAttribute User user,
                           ModelMap model) {
        userService.addUser(user);
        return allUsersList(model);
    }

    @GetMapping(value = "/redact")
    public String redactionForm(@RequestParam(value = "id") long id, ModelMap modelMap) {
        if (id != -1) {
            modelMap.addAttribute("user", userService.getUser(id));
        }
        return "redactuser";
    }

    @GetMapping(value = "/do_redact")
    public String redactionUser(@ModelAttribute User user, ModelMap modelMap) {
        userService.redactUser(user.getId(), user);
        return "redactionresult";
    }

}
