package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService {
    void add(Role role);
    void delete(Role role);
    void update(Role role);
    Role findById(long id);
    Role findByName(String name);
    public Set<Role> listRoles();
    public Role save (String name);
}
