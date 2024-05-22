package com.onebeld.pleasantvacation.service;

import com.onebeld.pleasantvacation.dto.trip.CreateTripDto;
import com.onebeld.pleasantvacation.entity.Image;
import com.onebeld.pleasantvacation.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TripService {
    void saveTrip(Trip trip, List<MultipartFile> images);

    List<Image> getImagesForTrip(Long tripId);

    Page<Trip> findAllTrips(Pageable pageable);

    Page<Trip> findAllTripsByPrice(long minPrice, long maxPrice, Pageable pageable);

    Page<Trip> findAllPurchasedTripsByUsername(String username, Pageable pageable);

    Page<Trip> findTripsByUsername(String username, Pageable pageable);

    Optional<Trip> findById(long id);

    long getBoundTripsCount(Trip trip);

    void deleteTrip(Trip trip);

    void updateTrip(CreateTripDto tripDto);
}
