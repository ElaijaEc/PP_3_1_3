package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Role getRole(String roleName) {
        List<Role> role = entityManager.createQuery("select role1 from Role role1 where role1.role=?1", Role.class)
                .setParameter(1, roleName)
                .getResultList();
        return role.size() > 0 ? role.get(0) : setNewRole(roleName);
    }
    private Role setNewRole(String roleName) {
        entityManager.persist(new Role(roleName));
        return getRole(roleName);
    }
}
