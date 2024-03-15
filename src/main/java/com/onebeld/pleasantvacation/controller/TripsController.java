package com.onebeld.pleasantvacation.controller;

import com.onebeld.pleasantvacation.dto.ReviewDto;
import com.onebeld.pleasantvacation.dto.trip.TripDto;
import com.onebeld.pleasantvacation.dto.trip.TripReducedDto;
import com.onebeld.pleasantvacation.entity.Review;
import com.onebeld.pleasantvacation.entity.Trip;
import com.onebeld.pleasantvacation.repository.ReviewRepository;
import com.onebeld.pleasantvacation.repository.TripRepository;
import com.onebeld.pleasantvacation.service.impl.ReviewServiceImpl;
import com.onebeld.pleasantvacation.service.impl.TripServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class TripsController {
    private final TripServiceImpl tripService;

    private final ReviewServiceImpl reviewService;

    /**
     * Создает экземпляр контроллера
     * @param tripRepository Репозиторий для работы с данными о путевках
     */
    TripsController(TripRepository tripRepository, ReviewRepository reviewRepository) {
        this.tripService = new TripServiceImpl(tripRepository);
        this.reviewService = new ReviewServiceImpl(reviewRepository);
    }

    /**
     * Получает список путевок
     * @param page Номер страницы
     * @param elementsInPage Количество элементов на одной странице
     * @return Список путевок
     */
    @GetMapping("/trips")
    @ResponseBody
    List<TripReducedDto> getAllTrips(@RequestParam int page, @RequestParam int elementsInPage) {
        List<TripReducedDto> trips = new ArrayList<>();

        Page<Trip> tripsPage = tripService.findAllTrips(PageRequest.of(page, elementsInPage));

        for (Trip trip : tripsPage) {
            trips.add(new TripReducedDto(trip));
        }

        return trips;
    }

    @GetMapping("/trips/{id}")
    @ResponseBody
    ResponseEntity<TripDto> getTrip(@PathVariable String id) {
        Optional<Trip> trip = tripService.findById(Long.parseLong(id));

        if (trip.isPresent()) {
            return new ResponseEntity<>(new TripDto(trip.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/trips/{id}/reviews")
    @ResponseBody
    ResponseEntity<List<ReviewDto>> getReviews(@PathVariable long id, @RequestParam int page, @RequestParam int elementsInPage) {
        List<ReviewDto> reviews = new ArrayList<>();

        Page<Review> reviewsPage = reviewService.findReviewsByTripId(id, PageRequest.of(page, elementsInPage));

        for (Review review : reviewsPage) {
            reviews.add(new ReviewDto(review));
        }

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
