package com.onebeld.pleasantvacation.repositories;

import com.onebeld.pleasantvacation.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface defines methods to access and manage the {@link Role} entity in the database.
 * This interface extends {@link JpaRepository}, which provides a default implementation
 * for most CRUD operations (basic database operations: create,
 * read, edit, and delete).
 *
 * @see JpaRepository
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Finds a role by its name.
     *
     * @param name Role name
     * @return Role with the given name, or {@code null} if not found
     */
    Role findByName(String name);
}
