package com.onebeld.pleasantvacation.service.impl;

import com.onebeld.pleasantvacation.entity.Trip;
import com.onebeld.pleasantvacation.repository.TripRepository;
import com.onebeld.pleasantvacation.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;

    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public Page<Trip> findAllTrips(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    @Override
    public Optional<Trip> findById(long id) {
        return tripRepository.findById(id);
    }
}
