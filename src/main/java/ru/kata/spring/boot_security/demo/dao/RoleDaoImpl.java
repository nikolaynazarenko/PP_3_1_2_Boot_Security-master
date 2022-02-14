package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.*;
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
    public void update(Role role) {
        entityManager.merge(role);
    }

    @Override
    public Set<Role> listRoles () {
    return Set.copyOf(entityManager.createQuery("select r from Role r",Role.class).getResultList());
    }


    @Override
    public Role findById(long id) throws NoResultException {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role findByName(String name) {
        TypedQuery <Role> query = entityManager.createQuery("select r from Role r where r.name = :name", Role.class);
        query.setParameter("name",name)
        .setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public void delete(Role role) {
        entityManager.remove(entityManager.merge(role));
    }

}

