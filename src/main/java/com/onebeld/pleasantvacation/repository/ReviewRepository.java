package com.onebeld.pleasantvacation.repository;

import com.onebeld.pleasantvacation.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
