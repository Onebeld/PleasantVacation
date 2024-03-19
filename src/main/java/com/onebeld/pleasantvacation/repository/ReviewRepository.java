package com.onebeld.pleasantvacation.repository;

import com.onebeld.pleasantvacation.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT * FROM reviews WHERE trip_id = :id",
            nativeQuery = true,
            countQuery = "SELECT COUNT(*) FROM reviews WHERE trip_id = :id")
    Page<Review> findReviewsByTripId(@Param("id") long id, Pageable pageable);
}
