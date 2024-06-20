package com.onebeld.pleasantvacation.services.implementations;

import com.onebeld.pleasantvacation.dtos.trip.CreateTripDto;
import com.onebeld.pleasantvacation.entities.Image;
import com.onebeld.pleasantvacation.entities.Ticket;
import com.onebeld.pleasantvacation.entities.Trip;
import com.onebeld.pleasantvacation.entities.User;
import com.onebeld.pleasantvacation.repositories.*;
import com.onebeld.pleasantvacation.services.ExchangeRateService;
import com.onebeld.pleasantvacation.services.FileStorageService;
import com.onebeld.pleasantvacation.services.interfaces.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Travel service.
 */
@Service
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final FileStorageService fileStorageService;
    private final ImageRepository imageRepository;
    private final ExchangeRateService exchangeRateService;

    /**
     * Constructor for initializing dependencies.
     *
     * @param tripRepository      Travel Repository
     * @param userRepository      User Repository
     * @param ticketRepository    Ticket Repository
     * @param fileStorageService  File storage service
     * @param imageRepository     Image repository
     * @param exchangeRateService Exchange rate service
     */
    public TripServiceImpl(TripRepository tripRepository, UserRepository userRepository, TicketRepository ticketRepository, FileStorageService fileStorageService, ImageRepository imageRepository, ExchangeRateService exchangeRateService) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.fileStorageService = fileStorageService;
        this.imageRepository = imageRepository;
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveTrip(Trip trip, List<MultipartFile> images) {
        tripRepository.save(trip);

        for (MultipartFile imageFile : images) {
            String imageUrl = fileStorageService.storeFile(imageFile);

            Image image = new Image();
            image.setUrl(imageUrl);
            image.setTrip(trip);

            imageRepository.save(image);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Image> getImagesForTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found with id " + tripId));

        return imageRepository.findAllByTrip(trip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Trip> findAllTripsByPrice(long minPrice, long maxPrice, Pageable pageable) {
        double convertedMinPrice = exchangeRateService.convertCurrency(minPrice);
        double convertedMaxPrice = exchangeRateService.convertCurrency(maxPrice);

        return tripRepository.findAllByPriceRange(convertedMinPrice, convertedMaxPrice, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Trip> findAllPurchasedTripsByUsername(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username " + username));

        Page<Ticket> tickets = ticketRepository.findTicketsByUser(user, pageable);

        return tickets.map(Ticket::getTrip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Trip> findTripsByUsername(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username " + username));
        return tripRepository.findTripsByUser(user, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Trip> findById(long id) {
        return tripRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTrip(Trip trip) {
        tripRepository.delete(trip);
    }

    /**
     * {@inheritDoc}
     */
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
                (long) tripDto.getImages().size() > 0 &&
                tripDto.getImages().stream().noneMatch(MultipartFile::isEmpty)) {
            for (Image image : imageRepository.findAllByTrip(trip)) {
                try {
                    fileStorageService.delete(image.getUrl());

                    imageRepository.delete(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (MultipartFile imageFile : tripDto.getImages()) {
                if (imageFile.isEmpty()) continue;

                String imageUrl = fileStorageService.storeFile(imageFile);

                Image image = new Image();
                image.setUrl(imageUrl);
                image.setTrip(trip);

                imageRepository.save(image);
            }
        }

        tripRepository.save(trip);
    }
}
