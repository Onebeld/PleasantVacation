package com.onebeld.pleasantvacation.service.impl;

import com.onebeld.pleasantvacation.dto.trip.CreateTripDto;
import com.onebeld.pleasantvacation.entity.Image;
import com.onebeld.pleasantvacation.entity.Ticket;
import com.onebeld.pleasantvacation.entity.Trip;
import com.onebeld.pleasantvacation.entity.User;
import com.onebeld.pleasantvacation.repository.*;
import com.onebeld.pleasantvacation.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final FileStorageServiceImpl fileStorageServiceImpl;
    private final ImageRepository imageRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public TripServiceImpl(TripRepository tripRepository, UserRepository userRepository, TicketRepository ticketRepository, FileStorageServiceImpl fileStorageServiceImpl, ImageRepository imageRepository, ExchangeRateRepository exchangeRateRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.fileStorageServiceImpl = fileStorageServiceImpl;
        this.imageRepository = imageRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public void saveTrip(Trip trip, List<MultipartFile> images) {
        tripRepository.save(trip);

        for (MultipartFile imageFile : images) {
            String imageUrl = fileStorageServiceImpl.storeFile(imageFile);

            Image image = new Image();
            image.setUrl(imageUrl);
            image.setTrip(trip);

            imageRepository.save(image);
        }
    }

    @Override
    public List<Image> getImagesForTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found with id " + tripId));

        return imageRepository.findAllByTrip(trip);
    }

    @Override
    public Page<Trip> findAllTrips(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    @Override
    public Page<Trip> findAllTripsByPrice(long minPrice, long maxPrice, Pageable pageable) {
        double convertedMinPrice = convertCurrency(minPrice);
        double convertedMaxPrice = convertCurrency(maxPrice);

        return tripRepository.findAllByPriceRange(convertedMinPrice, convertedMaxPrice, pageable);
    }

    @Override
    public Page<Trip> findAllPurchasedTripsByUsername(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username " + username));

        Page<Ticket> tickets = ticketRepository.findTicketsByUser(user, pageable);

        return tickets.map(Ticket::getTrip);
    }

    @Override
    public Page<Trip> findTripsByUsername(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username " + username));
        return tripRepository.findTripsByUser(user, pageable);
    }

    @Override
    public Optional<Trip> findById(long id) {
        return tripRepository.findById(id);
    }

    @Override
    public long getBoundTripsCount(Trip trip) {
        return ticketRepository.countByTrip(trip);
    }

    @Override
    public void deleteTrip(Trip trip) {
        tripRepository.delete(trip);
    }

    @Override
    public void updateTrip(CreateTripDto tripDto) {
        Trip trip = tripRepository.findById(tripDto.getId()).orElseThrow(() -> new RuntimeException("Trip not found with id " + tripDto.getId()));
        trip.setName(tripDto.getName());
        trip.setCity(tripDto.getCity());
        trip.setCountry(tripDto.getCountry());
        trip.setStartDate(tripDto.getStartDate());
        trip.setEndDate(tripDto.getEndDate());
        trip.setPrice(tripDto.getPrice());
        trip.setDescription(tripDto.getDescription());
        trip.setAllInclusive(tripDto.isAllInclusive());

        if (tripDto.getImages() != null &&
                (long)tripDto.getImages().size() > 0 &&
                tripDto.getImages().stream().noneMatch(MultipartFile::isEmpty)) {
            for (Image image : imageRepository.findAllByTrip(trip)) {
                try {
                    fileStorageServiceImpl.delete(image.getUrl());

                    imageRepository.delete(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (MultipartFile imageFile : tripDto.getImages()) {
                if (imageFile.isEmpty()) continue;

                String imageUrl = fileStorageServiceImpl.storeFile(imageFile);

                Image image = new Image();
                image.setUrl(imageUrl);
                image.setTrip(trip);

                imageRepository.save(image);
            }
        }

        tripRepository.save(trip);
    }

    private double convertCurrency(long price) {
        double value = price / exchangeRateRepository.findByCurrency("RUB").getRate();

        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }
}
