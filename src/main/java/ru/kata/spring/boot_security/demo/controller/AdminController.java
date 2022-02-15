package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping (value = "admin")
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
    public String showUsers (Principal principal, Model model) {
        List<User> users = userService.listUsers();
        model.addAttribute("users",users);
        User userP = userService.findByName(principal.getName());
        model.addAttribute("userP",userP);
        return "admin";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveUser (User userForm,
                            @RequestParam(name = "selectedRoles ",required = false) String [] selectedRoles){
        Set <Role> roleSet = new HashSet<>();
        for (String r : selectedRoles) {
            roleSet.add(roleService.save(r));
        }
        userForm.setRoles(roleSet);
        userService.add(userForm);
        return "redirect: /admin";
    }

    @RequestMapping  (value = "/edit/{id}",method = RequestMethod.GET)
    public String update (@PathVariable long id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "admin";
    }

    @RequestMapping (value = "/edit/{id}",method = RequestMethod.POST)
    public String updateUser (@ModelAttribute ("user") User userModal,
                              @RequestParam(name = "selectedRoles", required = false) String [] selectedRoles){
        if (selectedRoles != null) {
        Set<Role> roleSet = new HashSet<>();
            for (String r : selectedRoles) {roleSet.add(roleService.save(r));}
            userModal.setRoles(roleSet);
            userService.update(userModal);
        }
        userService.update(userModal);
        return "redirect:/admin";
    }

    @RequestMapping (value = "/delete/{id}",method = RequestMethod.POST)
    public String delete (@PathVariable long id){
        userService.delete(userService.findById(id));
        return "redirect: /admin";
    }

    @GetMapping("/newuser")
    public String registration (Principal principal, ModelMap model) {
        User userPrincipal = userService.findByName(principal.getName());
        model.addAttribute("userForm",new User());
        model.addAttribute("userPrincipal",userPrincipal);
        return "newuser";
    }
}
