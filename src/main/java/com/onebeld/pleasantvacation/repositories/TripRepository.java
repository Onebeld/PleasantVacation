package com.onebeld.pleasantvacation.repositories;

import com.onebeld.pleasantvacation.entities.Trip;
import com.onebeld.pleasantvacation.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * A repository for working with journeys.
 * This interface extends {@link JpaRepository}, which provides a default implementation
 * for most CRUD operations (basic database operations: create,
 * read, edit, and delete).
 *
 * @see JpaRepository
 */
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    /**
     * Finds the travel page for the specified user.
     *
     * @param user     User
     * @param pageable Page Options
     * @return Trips Page
     */
    Page<Trip> findTripsByUser(User user, Pageable pageable);

    /**
     * Finds a page of trips with a price in the specified range.
     *
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @param pageable Page Options
     * @return Trips Page
     */
    @Query(value = "SELECT * FROM trips WHERE price BETWEEN :minPrice AND :maxPrice", nativeQuery = true)
    Page<Trip> findAllByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);
}
