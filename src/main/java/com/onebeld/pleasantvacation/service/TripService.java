package com.onebeld.pleasantvacation.service;

import com.onebeld.pleasantvacation.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TripService {
    Page<Trip> findAllTrips(Pageable pageable);

    Optional<Trip> findById(long id);
}
