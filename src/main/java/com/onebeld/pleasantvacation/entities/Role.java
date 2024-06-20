package com.onebeld.pleasantvacation.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * The class represents a role entity that is used to manage user access.
 * It implements the {@link GrantedAuthority} interface, which is part of Spring Security.
 */
@Entity
@Table(name = "t_role")
public class Role implements GrantedAuthority {
    @Id
    private long id;

    private String name;

    /**
     * A collection of users associated with this role.
     * This field will not be saved to the database because it is marked with the @Transient annotation.
     */
    @Transient
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private Set<User> users;

    /**
     * Default constructor.
     */
    public Role() {
    }

    /**
     * Constructor with the id parameter.
     *
     * @param id A unique identifier for the role.
     */
    public Role(long id) {
        this.id = id;
    }

    /**
     * Constructor with id and name parameters.
     *
     * @param id   The unique identifier of the role.
     * @param name The name of the role.
     */
    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the unique identifier of the role.
     *
     * @return A unique identifier for the role.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the role.
     *
     * @param id A unique identifier for the role.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the name of the role.
     *
     * @return Role Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     *
     * @param name Role Name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a collection of users associated with the given role.
     *
     * @return A collection of users associated with a given role.
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Sets the collection of users associated with this role.
     *
     * @param users A collection of users associated with a given role.
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Returns the name of the role that is used as an authority.
     * This method implements the {@link GrantedAuthority} interface.
     *
     * @return Role Name.
     */
    @Override
    public String getAuthority() {
        return getName();
    }
}
