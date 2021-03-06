package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService  {
    void add(User user);
    void delete(User user);
    void update(User user);
    User findById(long id);
    User findByName(String name);
    List<User> listUsers();
    public void addNewUser(User user);
}
