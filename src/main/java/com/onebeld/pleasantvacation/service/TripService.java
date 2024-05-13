package com.onebeld.pleasantvacation.service;

import com.onebeld.pleasantvacation.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TripService {
    Page<Trip> findAllTrips(Pageable pageable);

    Page<Trip> findAllPurchasedTripsByUsername(String username, Pageable pageable);

    Page<Trip> findTripsByUsername(String username, Pageable pageable);

    Optional<Trip> findById(long id);
}
