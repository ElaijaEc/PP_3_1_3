package ru.kata.spring.boot_security.demo.dao;



import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    void removeUser(long id);
    User getUser(long id);
    User getUser(String username);
    User redactUser(long id, User updated);
    List<User> getUserList();
}
