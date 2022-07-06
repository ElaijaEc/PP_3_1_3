package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "firstname") // имя
    private String firstName;
    @Column(name = "lastname") // фамилия
    private String lastName;
    @Column(name = "age",columnDefinition = "int  default 0") // возраст
    private int age;
    @Column(name = "login") // login
    private String login;
    @Column(name = "Email") //почта
    private String Email;
    @Column(name = "password") // пароль
    private String password;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> authorities = new HashSet<>();
    //Конструкторы
    public User() {
    }
    //Методы
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Set<Role> roles) {
        this.authorities = roles;
    }
    @Override
    public Set<Role> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return login;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User " +
                "id=" + id +
                ", firstName= " + firstName + '\'' +
                ", lastName= " + lastName + '\'' +
                ", age= " + age +
                ", username= " + login + '\'' +
                ", password= " + password + '\'' +
                ", roles= " + authorities ;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && id.equals(user.id) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                authorities.equals(user.authorities);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, login, password, authorities);
    }
}


