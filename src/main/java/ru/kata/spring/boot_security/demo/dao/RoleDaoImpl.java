package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void update() {

    }

    @Override
    public Set<Role> roleSet() {
        return null;
    }

    @Override
    public Role findById(long id) {
        return entityManager.find(Role.class, id);
    }

    public Role findByName(String name) {
        try {
            Query query = entityManager.createQuery("select r from Role r where r.name = :name", Role.class);
            query.setParameter("name", name);
            return (Role) query.getSingleResult();
        } catch (NoResultException e) {
            entityManager.persist(new Role(name));
            return findByName(name);
        }
    }

}

