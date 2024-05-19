package com.onebeld.pleasantvacation.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "t_role")
public class Role implements GrantedAuthority {
    @Id
    private long id;

    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {

    }

    public Role(long id) {
        this.id = id;
    }

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}