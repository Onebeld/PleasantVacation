package com.onebeld.pleasantvacation.service.impl;

import com.onebeld.pleasantvacation.entity.Image;
import com.onebeld.pleasantvacation.entity.Ticket;
import com.onebeld.pleasantvacation.entity.Trip;
import com.onebeld.pleasantvacation.entity.User;
import com.onebeld.pleasantvacation.repository.ImageRepository;
import com.onebeld.pleasantvacation.repository.TicketRepository;
import com.onebeld.pleasantvacation.repository.TripRepository;
import com.onebeld.pleasantvacation.repository.UserRepository;
import com.onebeld.pleasantvacation.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final FileStorageServiceImpl fileStorageServiceImpl;
    private final ImageRepository imageRepository;

    public TripServiceImpl(TripRepository tripRepository, UserRepository userRepository, TicketRepository ticketRepository, FileStorageServiceImpl fileStorageServiceImpl, ImageRepository imageRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.fileStorageServiceImpl = fileStorageServiceImpl;
        this.imageRepository = imageRepository;
    }

    @Override
    public void saveTrip(Trip trip, List<MultipartFile> images) {
        for (MultipartFile imageFile : images) {
            String imageUrl = fileStorageServiceImpl.storeFile(imageFile);

            Image image = new Image();
            image.setUrl(imageUrl);
            image.setTrip(trip);

            imageRepository.save(image);
        }

        tripRepository.save(trip);
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
}
