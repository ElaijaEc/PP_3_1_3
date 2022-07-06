package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }
    @Override
    public void removeUser(long id) {
        entityManager.remove(getUser(id));
    }
    @Override
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }
    public User getUser(String login) {
        List<User> list = entityManager
                .createQuery("select distinct user from User user join fetch user.authorities roles where user.login=?1",
                        User.class)
                .setParameter(1, login).getResultList();
        System.out.println(list);
        return list.size() > 0 ? list.get(0) : null;
    }
    @Override
    public User redactUser(long id, User updated) {
        User user = entityManager.find(User.class, id);
        user.setAge(updated.getAge());
        user.setEmail(updated.getEmail());
        user.setLogin(updated.getLogin());
        user.setPassword(updated.getPassword());
        user.setLastName(updated.getLastName());
        user.setFirstName(updated.getFirstName());
        user.setAuthorities(updated.getAuthorities());
        entityManager.merge(user);
        return user;
    }
    @Override
    public List<User> getUserList() {
        return entityManager
                .createQuery("select distinct user from User user join fetch user.authorities roles order by user.id",
                        User.class).getResultList();
    }
}

