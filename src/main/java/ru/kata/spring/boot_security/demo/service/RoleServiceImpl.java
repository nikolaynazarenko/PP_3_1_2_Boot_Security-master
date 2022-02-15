package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;


import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private RoleDao roleDao;

    @Autowired
    public void setRoleDao (RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void add(Role role) {
        roleDao.add(role);
    }

    @Override
    public void delete(Role role) {
        roleDao.delete(role);
    }

    @Override
    public void update(Role role) {
    roleDao.update(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findById(long id) {
           return roleDao.findById(id);}

    @Override
    @Transactional(readOnly = true)
    public Role findByName(String name) {return roleDao.findByName(name);}

    @Override
    @Transactional(readOnly = true)
    public Set<Role> listRoles() {
        return roleDao.listRoles();
    }
    @Override
    public Role save (String name) {
        if (roleDao.findByName(name) == null){
            Role role = new Role(name);
            return role;
        } return roleDao.findByName(name);
    }
}

