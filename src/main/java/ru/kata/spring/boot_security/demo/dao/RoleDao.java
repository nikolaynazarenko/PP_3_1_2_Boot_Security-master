package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {

    void add (Role role);
    void update ();
    Set<Role> roleSet();
    Role findById(long id);
    Role findByName(String name);
}
