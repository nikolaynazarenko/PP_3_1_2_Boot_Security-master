package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleDao roleDao;

    @Override
    public void add(Role role) {

    }

    @Override
    public void delete(Role role) {

    }

    @Override
    public void update(Role role) {

    }

    @Override
    public Role findById(long id) {
        return null;
    }

    @Override
    public Role findByName(String name) {return roleDao.findByName(name);}
}
