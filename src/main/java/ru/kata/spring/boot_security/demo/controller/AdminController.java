package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String showUsers (Model model) {
        List<User> users = userService.listUsers();
        model.addAttribute("users",users);
        return "admin";
    }

    @PostMapping ("/admin/{id}/update")
    public String update (@PathVariable long id, Model model){
        model.addAttribute("user",userService.findById(id));
        return "updateuser";
    }

    @PostMapping("/admin/{id}")
    public String updateUser (@ModelAttribute ("user") User user,@PathVariable long id){
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping ("admin/delete/{id}")
    public String delete (@PathVariable long id){
        userService.delete(userService.findById(id));
        return "redirect:/admin";
    }
}
