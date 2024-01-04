package com.onebeld.pleasantvacation.repository;

import com.onebeld.pleasantvacation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
