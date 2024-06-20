package com.onebeld.pleasantvacation.repositories;

import com.onebeld.pleasantvacation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository for user experience.
 * This interface extends {@link JpaRepository}, which provides a default implementation
 * for most CRUD operations (basic database operations: create,
 * read, edit, and delete).
 *
 * @see JpaRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username Username
     * @return User (if found), otherwise empty {@link Optional}
     */
    Optional<User> findByUsername(String username);
}
