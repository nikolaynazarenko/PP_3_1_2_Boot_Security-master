package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showUsers (Model model) {
        List<User> users = userService.listUsers();
        model.addAttribute("users",users);
        return "admin";
    }

    @RequestMapping (value = "{id}/update",method = RequestMethod.GET)
    public String update (@PathVariable long id, Model model){
        model.addAttribute("user",userService.findById(id));
        return "updateuser";
    }

    @PostMapping("/admin/{id}")
    public String updateUser (@ModelAttribute ("user") User user,
                              @RequestParam(name = "selectedRoles", required = false) String [] selectedRoles){
        if (selectedRoles != null) {
            Set<Role> roleSet = new HashSet<>();
            for (String r : selectedRoles) {roleSet.add(roleService.save(r));}
            user.setRoles(roleSet);
            userService.update(user);
        }
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping ("/delete/{id}")
    public String delete (@PathVariable long id){
        userService.delete(userService.findById(id));
        return "redirect:/admin";
    }
}
