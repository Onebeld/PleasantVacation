package com.onebeld.pleasantvacation.services.interfaces;

import com.onebeld.pleasantvacation.dtos.trip.CreateTripDto;
import com.onebeld.pleasantvacation.entities.Image;
import com.onebeld.pleasantvacation.entities.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * The TripService interface provides methods for managing trips and their associated images.
 */
public interface TripService {
    /**
     * Saves the trip and associated images.
     *
     * @param trip   A trip that needs to be saved.
     * @param images Images related to the trip.
     */
    void saveTrip(Trip trip, List<MultipartFile> images);

    /**
     * Gets a list of images for the given trip.
     *
     * @param tripId Trip ID.
     * @return A list of images for the trip.
     */
    List<Image> getImagesForTrip(Long tripId);

    /**
     * Gets all trips in a given price range, page by page.
     *
     * @param minPrice Minimum travel cost.
     * @param maxPrice Maximum cost of trips.
     * @param pageable Pagination information.
     * @return Page trips within the price range.
     */
    Page<Trip> findAllTripsByPrice(long minPrice, long maxPrice, Pageable pageable);

    /**
     * Retrieves all purchased trips for this user on a page-by-page basis.
     *
     * @param username Username.
     * @param pageable Pagination information.
     * @return A page of purchased trips for the user.
     */
    Page<Trip> findAllPurchasedTripsByUsername(String username, Pageable pageable);

    /**
     * Gets all trips for this user page by page.
     *
     * @param username Username.
     * @param pageable Pagination information.
     * @return Travel page for the user.
     */
    Page<Trip> findTripsByUsername(String username, Pageable pageable);

    /**
     * Retrieves a trip by its trip ID.
     *
     * @param id Trip ID.
     * @return Optional value containing the trip if found, otherwise empty.
     */
    Optional<Trip> findById(long id);

    /**
     * Deletes the trip.
     *
     * @param trip A trip to be deleted.
     */
    void deleteTrip(Trip trip);

    /**
     * Updates the trip information.
     *
     * @param tripDto Trip Information.
     */
    void updateTrip(CreateTripDto tripDto);
}
