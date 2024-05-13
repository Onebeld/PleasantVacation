package com.onebeld.pleasantvacation.repository;

import com.onebeld.pleasantvacation.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query(value = "SELECT * FROM tickets " +
            "JOIN trips ON tickets.trip_id = trips.id JOIN users ON tickets.user_id = users.id WHERE users.username = :username",
            nativeQuery = true)
    Page<Trip> findPurchasedTripsByUsername(@Param("username") String username, Pageable pageable);

    @Query(value = "SELECT * FROM trips JOIN users ON trips.user_id = users.id WHERE users.username = :username",
            nativeQuery = true)
    Page<Trip> findTripsByUsername(@Param("username") String username, Pageable pageable);
}
