package com.onebeld.pleasantvacation.repository;

import com.onebeld.pleasantvacation.entity.Trip;
import com.onebeld.pleasantvacation.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    Page<Trip> findTripsByUser(User user, Pageable pageable);

    @Query(value = "SELECT * FROM trips WHERE price BETWEEN :minPrice AND :maxPrice", nativeQuery = true)
    Page<Trip> findAllByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);
}
