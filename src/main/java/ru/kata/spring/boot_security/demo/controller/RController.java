package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping(value = "/rest/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class RController {

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

    @GetMapping
    public List<User> showUsers() {
        return userService.listUsers();
    }

    @GetMapping(value = "/{id}")
    public User singleUSer(@PathVariable long id) {
        return userService.findById(id);
    }

    @PostMapping
    public void newUser (User user){
        Set <Role> roles = new HashSet<>();
        for (Role r : user.getRoles()) {
            roles.add(roleService.save(r.getName()));
        }
        user.setRoles(roles);
        userService.add(user);
    }

    @PutMapping(value = "/{id}",produces = "application/json")
    public ResponseEntity <?> update ( User user) {
        try {
            Set<Role> roles = new HashSet<>();
            for (Role r : user.getRoles()) {
                roles.add(roleService.save(r.getName()));
            }
            user.setRoles(roles);
            userService.update(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException r) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> delete(User user) {
        try {
            userService.delete(userService.findById(user.getId()));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity<User> userPage(Principal principal) {
        try {
            User user = userService.findByName(principal.getName());
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (NoSuchElementException r) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


