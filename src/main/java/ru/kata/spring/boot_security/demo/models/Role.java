package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "role")
    String role;
    @ManyToMany(mappedBy = "roles")
    Set<User> users;
    // методы
    @Override
    public String getAuthority() {
        return getRole();
    }
    //конструкторы
    public Role() {
    }
    public Role(String role) {
        this.role = role;
    }
    //геттеры и сеттеры
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Роль = ").append(role).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return id == role1.id && role.equals(role1.role) && users.equals(role1.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, users);
    }
}
