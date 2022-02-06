package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/new_user")
    public String registration ()
    {
        return "new_user";
    }

    @PostMapping("/save_user")
    public String saveUser (@ModelAttribute User user){
        userService.add(user);
        return "redirect:/";
    }

    @GetMapping("/user")
    public String userPage (Principal principal,User user, Model model){
        if (user != null){
            user = userService.findByName(principal.getName());
            model.addAttribute("user",user);
            return "user";
        }
        return "redirect:/";
    }

}
