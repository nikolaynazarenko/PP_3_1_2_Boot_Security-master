package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private BCryptPasswordEncoder encoder;



    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public void add(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.add(user);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }

    @Transactional()
    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Transactional
    @Override
    public void addNewUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getUsername().contains("admin")) {
            user.setRoles(Set.of(roleDao.findByName("ROLE_ADMIN"),roleDao.findByName("ROLE_USER")));
        } else
            user.setRoles(Set.of(roleDao.findByName("ROLE_USER")));
        userDao.add(user);
    }
}
