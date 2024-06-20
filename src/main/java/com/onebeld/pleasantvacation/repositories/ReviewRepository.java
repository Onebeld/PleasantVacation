package com.onebeld.pleasantvacation.repositories;

import com.onebeld.pleasantvacation.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * An interface that provides a repository for handling the {@link Review} entity.
 * This interface extends {@link JpaRepository}, which provides a default implementation
 * for most CRUD operations (basic database operations: create,
 * read, edit, and delete).
 *
 * @see JpaRepository
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
    /**
     * Finds all reviews for a given travel ID.
     *
     * @param id       Trip ID
     * @param pageable Object {@link Pageable} for pagination
     * @return Reviews page
     */
    @Query(value = "SELECT * FROM reviews WHERE trip_id = :id",
            nativeQuery = true,
            countQuery = "SELECT COUNT(*) FROM reviews WHERE trip_id = :id")
    Page<Review> findReviewsByTripId(@Param("id") long id, Pageable pageable);
}
