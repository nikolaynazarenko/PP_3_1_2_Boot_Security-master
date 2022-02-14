package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleDao {

    void add (Role role);
    void update (Role role);
    public Set<Role> listRoles();
    Role findById(long id);
    Role findByName(String name);
    public void delete(Role role);
}
