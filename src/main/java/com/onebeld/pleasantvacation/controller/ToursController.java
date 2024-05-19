package com.onebeld.pleasantvacation.controller;

import com.onebeld.pleasantvacation.dto.review.ReviewDto;
import com.onebeld.pleasantvacation.dto.review.ReviewSubmitDTO;
import com.onebeld.pleasantvacation.dto.trip.CreateTripDto;
import com.onebeld.pleasantvacation.dto.trip.TripDto;
import com.onebeld.pleasantvacation.dto.trip.TripReducedDto;
import com.onebeld.pleasantvacation.dto.trip.TripsDto;
import com.onebeld.pleasantvacation.entity.*;
import com.onebeld.pleasantvacation.entity.enums.TicketState;
import com.onebeld.pleasantvacation.entity.enums.TripState;
import com.onebeld.pleasantvacation.repository.ImageRepository;
import com.onebeld.pleasantvacation.repository.TicketRepository;
import com.onebeld.pleasantvacation.repository.TripRepository;
import com.onebeld.pleasantvacation.repository.UserRepository;
import com.onebeld.pleasantvacation.service.impl.FileStorageServiceImpl;
import com.onebeld.pleasantvacation.service.impl.ReviewServiceImpl;
import com.onebeld.pleasantvacation.service.impl.TripServiceImpl;
import com.onebeld.pleasantvacation.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ToursController {
    private final TripServiceImpl tripService;
    private final UserServiceImpl userService;

    private final TicketRepository ticketRepository;
    private final ReviewServiceImpl reviewService;
    private final ImageRepository imageRepository;

    /**
     * Создает экземпляр контроллера
     * @param tripRepository Репозиторий для работы с данными о путевках
     */
    ToursController(TripRepository tripRepository,
                    UserServiceImpl userService,
                    TicketRepository ticketRepository,
                    ReviewServiceImpl reviewService,
                    UserRepository userRepository,
                    FileStorageServiceImpl fileStorageServiceImpl,
                    ImageRepository imageRepository) {
        this.tripService = new TripServiceImpl(tripRepository, userRepository, ticketRepository, fileStorageServiceImpl, imageRepository);
        this.userService = userService;
        this.ticketRepository = ticketRepository;
        this.reviewService = reviewService;
        this.imageRepository = imageRepository;
    }

    @RequestMapping("/tours")
    public String hub() {
        return "tours";
    }

    @RequestMapping("/tours/{id}")
    public String tour(@PathVariable String id, Model model) {
        Optional<Trip> trip = tripService.findById(Long.parseLong(id));

        if (trip.isPresent()) {
            TripDto tripDto = new TripDto(trip.get());

            List<Image> images = imageRepository.findAllByTrip(trip.get());
            tripDto.setImageUrls(images.stream().map(Image::getUrl).toList());

            model.addAttribute("trip", tripDto);
            model.addAttribute("review", new ReviewSubmitDTO());

            return "trip";
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found");
    }

    @PostMapping("/tours/{id}/buy")
    public String buy(@PathVariable String id, Model model, Principal principal) {
        Optional<Trip> trip = tripService.findById(Long.parseLong(id));

        if (trip.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found");

        Optional<User> user = userService.findUserByUsername(principal.getName());

        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        if (ticketRepository.existsTicketByUserAndTrip(user.get(), trip.get()))
        {
            model.addAttribute("tripIsAlreadyOrdered", true);
            return "redirect:/tours/" + id;
        }

        ticketRepository.save(new Ticket(user.get(), trip.get(), TicketState.PENDING));

        return "redirect:/thanks_for_buying";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/tours/{id}/reviews/add")
    String addReview(@PathVariable long id, @ModelAttribute("review") @Valid ReviewSubmitDTO reviewSubmitDTO, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/tours/" + id;
        }

        Optional<User> userDto = userService.findUserByUsername(principal.getName());
        Optional<Trip> trip = tripService.findById(id);

        if (userDto.isEmpty() || trip.isEmpty()) {
            return "redirect:/tours/" + id;
        }

        Review review = new Review();
        review.setText(reviewSubmitDTO.getText());
        review.setUser(userDto.get());
        review.setTrip(trip.get());
        review.setDate(new Timestamp(System.currentTimeMillis()));
        review.setRating((short) 5);

        reviewService.saveReview(review);

        return "redirect:/tours/" + id;
    }

    @PreAuthorize("hasAuthority('TOURMANAGER')")
    @GetMapping("/tours/add")
    public String addTrip(Model model) {
        model.addAttribute("trip", new CreateTripDto());

        return "add_tour";
    }

    @PreAuthorize("hasAuthority('TOURMANAGER')")
    @PostMapping("/tours/add")
    public String addTripPost(@ModelAttribute("trip") @Valid CreateTripDto tripDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "add_tour";
        }

        String username = principal.getName();
        Optional<User> userDto = userService.findUserByUsername(username);

        if (userDto.isEmpty()) {
            return "add_tour";
        }

        Trip trip = new Trip(tripDto);
        trip.setUser(userDto.get());
        trip.setState(TripState.ACTIVE);

        tripService.saveTrip(trip, tripDto.getImages());

        return "redirect:/tours/" + trip.getId();
    }

    @GetMapping("/api/tours/{id}/reviews")
    @ResponseBody
    ResponseEntity<List<ReviewDto>> getReviews(@PathVariable long id, @RequestParam int page, @RequestParam int elementsInPage) {
        List<ReviewDto> reviews = new ArrayList<>();

        Page<Review> reviewsPage = reviewService.findReviewsByTripId(id, PageRequest.of(page, elementsInPage));

        for (Review review : reviewsPage) {
            reviews.add(new ReviewDto(review));
        }

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/api/tours/{id}")
    @ResponseBody
    ResponseEntity<TripDto> getTrip(@PathVariable String id) {
        Optional<Trip> trip = tripService.findById(Long.parseLong(id));

        if (trip.isPresent()) {
            return new ResponseEntity<>(new TripDto(trip.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Получает список путевок
     * @param page Номер страницы
     * @param elementsInPage Количество элементов на одной странице
     * @return Список путевок
     */
    @GetMapping("/api/tours")
    @ResponseBody
    TripsDto getAllTrips(@RequestParam int page, @RequestParam int elementsInPage) {
        List<TripReducedDto> trips = new ArrayList<>();

        Page<Trip> tripsPage = tripService.findAllTrips(PageRequest.of(page, elementsInPage));

        return getTripsDto(page, elementsInPage, tripsPage, trips);
    }

    @PreAuthorize("hasAuthority('TOURMANAGER')")
    @GetMapping("/api/tours/tourmanager")
    @ResponseBody
    TripsDto getTripsForManager(@RequestParam int page, @RequestParam int elementsInPage, Principal principal) {
        List<TripReducedDto> trips = new ArrayList<>();

        Page<Trip> tripsPage = tripService.findTripsByUsername(principal.getName(), PageRequest.of(page, elementsInPage));

        return getTripsDto(page, elementsInPage, tripsPage, trips);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/api/tours/bought")
    @ResponseBody
    TripsDto getBoughtTrips(@RequestParam int page, @RequestParam int elementsInPage, Principal principal) {
        List<TripReducedDto> trips = new ArrayList<>();

        Page<Trip> tripsPage = tripService.findAllPurchasedTripsByUsername(principal.getName(), PageRequest.of(page, elementsInPage));

        return getTripsDto(page, elementsInPage, tripsPage, trips);
    }

    private TripsDto getTripsDto(int page, int elementsInPage, Page<Trip> tripsPage, List<TripReducedDto> trips) {
        TripsDto tripsDto = new TripsDto();

        tripsDto.setCurrentPage(page);
        tripsDto.setElementsOnPage(elementsInPage);
        tripsDto.setTotalPages(tripsPage.getTotalPages());
        tripsDto.setTotalElements(tripsPage.getTotalElements());

        for (Trip trip : tripsPage) {
            TripReducedDto tripReducedDto = new TripReducedDto(trip);
            tripReducedDto.setImage(imageRepository.findAllByTrip(trip).getFirst().getUrl());

            trips.add(tripReducedDto);
        }

        tripsDto.setTrips(trips);
        return tripsDto;
    }
}
