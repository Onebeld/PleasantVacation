package com.onebeld.pleasantvacation.service.impl;

import com.onebeld.pleasantvacation.entity.Trip;
import com.onebeld.pleasantvacation.repository.TripRepository;
import com.onebeld.pleasantvacation.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;

    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public void saveTrip(Trip trip) {
        tripRepository.save(trip);
    }

    @Override
    public Page<Trip> findAllTrips(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    @Override
    public Page<Trip> findAllPurchasedTripsByUsername(String username, Pageable pageable) {
        return tripRepository.findPurchasedTripsByUsername(username, pageable);
    }

    @Override
    public Page<Trip> findTripsByUsername(String username, Pageable pageable) {
        return tripRepository.findTripsByUsername(username, pageable);
    }

    @Override
    public Optional<Trip> findById(long id) {
        return tripRepository.findById(id);
    }
}
