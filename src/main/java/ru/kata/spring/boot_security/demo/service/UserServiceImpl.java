package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private RoleDao roleDao;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }
    private void checkRolesForUser(User user) {
        Set<Role> checkedRoles = new HashSet<>();
        for (Role role: user.getRoles()) {
            checkedRoles.add(roleDao.getRole(role.getRole()));
        }
        user.setRoles(checkedRoles);
    }
    @Transactional
    @Override
    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        checkRolesForUser(user);
        userDao.addUser(user);
    }
    @Transactional
    @Override
    public void removeUser(long id) {
        userDao.removeUser(id);
    }
    @Transactional
    @Override
    public User getUser(long id) {
        return userDao.getUser(id);
    }
    @Transactional
    @Override
    public User redactUser(long id, User updated) {
        updated.setPassword(passwordEncoder.encode(updated.getPassword()));
        checkRolesForUser(updated);
        return userDao.redactUser(id, updated);
    }
    @Transactional
    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }
}
