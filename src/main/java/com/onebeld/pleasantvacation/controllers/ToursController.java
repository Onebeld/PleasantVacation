package com.onebeld.pleasantvacation.controllers;

import com.onebeld.pleasantvacation.dtos.review.ReviewDto;
import com.onebeld.pleasantvacation.dtos.review.ReviewSubmitDto;
import com.onebeld.pleasantvacation.dtos.review.ReviewsDto;
import com.onebeld.pleasantvacation.dtos.trip.CreateTripDto;
import com.onebeld.pleasantvacation.dtos.trip.TripDto;
import com.onebeld.pleasantvacation.dtos.trip.TripReducedDto;
import com.onebeld.pleasantvacation.dtos.trip.TripsDto;
import com.onebeld.pleasantvacation.entities.*;
import com.onebeld.pleasantvacation.repositories.ImageRepository;
import com.onebeld.pleasantvacation.repositories.TicketRepository;
import com.onebeld.pleasantvacation.repositories.TripRepository;
import com.onebeld.pleasantvacation.repositories.UserRepository;
import com.onebeld.pleasantvacation.services.ExchangeRateService;
import com.onebeld.pleasantvacation.services.FileStorageService;
import com.onebeld.pleasantvacation.services.StatisticService;
import com.onebeld.pleasantvacation.services.implementations.ReviewServiceImpl;
import com.onebeld.pleasantvacation.services.implementations.TripServiceImpl;
import com.onebeld.pleasantvacation.services.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The class is responsible for handling requests related to tours.
 */
@Controller
public class ToursController {
    private final TripServiceImpl tripService;
    private final UserServiceImpl userService;
    private final ReviewServiceImpl reviewService;

    private final FileStorageService fileStorageService;
    private final StatisticService statisticService;

    private final TicketRepository ticketRepository;
    private final ImageRepository imageRepository;

    private final ExchangeRateService exchangeRateService;

    /**
     * Creates an instance of the controller
     *
     * @param tripRepository Repository for working with travel data
     */
    ToursController(TripRepository tripRepository,
                    UserServiceImpl userService,
                    TicketRepository ticketRepository,
                    ReviewServiceImpl reviewService,
                    UserRepository userRepository,
                    FileStorageService fileStorageService,
                    ImageRepository imageRepository,
                    StatisticService statisticService,
                    ExchangeRateService exchangeRateService) {
        this.statisticService = statisticService;
        this.tripService = new TripServiceImpl(tripRepository, userRepository, ticketRepository, fileStorageService, imageRepository, exchangeRateService);
        this.userService = userService;
        this.ticketRepository = ticketRepository;
        this.reviewService = reviewService;
        this.imageRepository = imageRepository;
        this.fileStorageService = fileStorageService;
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * Retrieves the tours page
     *
     * @return Title of tours page
     */
    @RequestMapping("/tours")
    String getToursPage() {
        return "tours";
    }

    /**
     * Gets the trip page for the specified trip ID.
     *
     * @param id             Trip ID
     * @param model          Model object for transferring data to the view
     * @param authentication Authentication object to verify the user's authorization
     * @return The name of the view to be displayed
     */
    @RequestMapping("/tours/{id}")
    String getTripPage(@PathVariable String id, Model model, Authentication authentication) {
        Optional<Trip> trip = tripService.findById(Long.parseLong(id));

        if (trip.isPresent()) {
            // Receiving a voucher and converting the price into rubles
            TripDto tripDto = new TripDto(trip.get());
            tripDto.setPrice(exchangeRateService.convertCurrency(trip.get().getPrice()));

            // Retrieving a list of images
            List<Image> images = imageRepository.findAllByTrip(trip.get());
            tripDto.setImageUrls(images.stream().map(Image::getUrl).toList());

            model.addAttribute("trip", tripDto);
            model.addAttribute("review", new ReviewSubmitDto());

            if (authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("TOURMANAGER"))) {
                Optional<User> user = userService.findUserByUsername(authentication.getName());

                if (user.isEmpty())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

                // Check whether the user has created this trip
                if (!trip.get().getUser().equals(user.get())) {
                    model.addAttribute("createdByMe", false);

                    return "trip";
                }

                long ticketsCount = ticketRepository.countByTrip(trip.get());
                double totalEarned = tripDto.getPrice() * ticketsCount;

                // Brief statistics on the trip
                model.addAttribute("ticketsCount", ticketsCount);
                model.addAttribute("totalEarned", totalEarned);

                model.addAttribute("createdByMe", true);
            }

            return "trip";
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found");
    }

    /**
     * Deletes the trip with the specified ID if the authenticated user has 'TOURMANAGER' authority.
     *
     * @param id        ID of the trip to be deleted
     * @param principal Authenticated user
     * @return Redirects to the '/tours' page if the trip is successfully deleted, otherwise throws an {@link ResponseStatusException} exception with the appropriate status code
     */
    @PreAuthorize("hasAuthority('TOURMANAGER')")
    @PostMapping("/tours/{id}/delete")
    String deleteTrip(@PathVariable String id, Principal principal) {
        Optional<Trip> trip = tripService.findById(Long.parseLong(id));

        // Проверка, существует ли путевка
        if (trip.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found");

        // Проверка, создал ли пользователь данную путевку
        if (!trip.get().getUser().getUsername().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't delete this trip");

        // Удаление изображений
        for (Image image : imageRepository.findAllByTrip(trip.get())) {
            try {
                fileStorageService.delete(image.getUrl());
                imageRepository.delete(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tripService.deleteTrip(trip.get());

        return "redirect:/tours";
    }

    /**
     * Retrieves the trip for editing by the tour manager.
     *
     * @param id        Trip ID for editing
     * @param model     Model object for transferring data to the view
     * @param principal The main object representing the authenticated user
     * @return The name of the view to render
     */
    @PreAuthorize("hasAuthority('TOURMANAGER')")
    @GetMapping("/tours/{id}/edit")
    String editTrip(@PathVariable String id, Model model, Principal principal) {
        Optional<Trip> trip = tripService.findById(Long.parseLong(id));

        // Проверка, существует ли путевка
        if (trip.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found");

        // Проверка, создал ли пользователь данную путевку
        if (!trip.get().getUser().getUsername().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't edit this trip");

        CreateTripDto tripDto = new CreateTripDto(trip.get());

        model.addAttribute("trip", tripDto);

        return "edit_tour";
    }

    /**
     * Returns statistics for the specified tour in PDF format if the user is authorized to view them.
     *
     * @param id        Tour ID
     * @param principal The main object representing the authenticated user
     * @return Response with PDF file of statistics if user is authorized to view it, otherwise error 403 or 404
     * @throws Exception If an error occurred while generating PDF file
     */
    @PreAuthorize("hasAuthority('TOURMANAGER')")
    @GetMapping("/tours/{id}/statistics")
    ResponseEntity<ByteArrayResource> getStatisticsForTrip(@PathVariable String id, Principal principal) throws Exception {
        Optional<Trip> trip = tripService.findById(Long.parseLong(id));

        // Проверка, существует ли путевка
        if (trip.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        // Проверка, создал ли пользователь данную путевку
        if (!trip.get().getUser().getUsername().equals(principal.getName()))
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        // Поток, необходимый для записи PDF-файла в массив байтов
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        statisticService.createStatisticPdf(LocaleContextHolder.getLocale(), trip.get().getUser(), byteArrayOutputStream, Collections.singletonList(trip.get()));

        ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=statistics.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    /**
     * Returns statistics for all tours in PDF format if the user is authorized to view them.
     *
     * @param principal The main object representing the authenticated user
     * @return Response with PDF file of statistics if user is authorized to view it, otherwise error 403 or 404
     * @throws IOException If an error occurs while reading or writing data
     */
    @PreAuthorize("hasAuthority('TOURMANAGER')")
    @GetMapping("/tours/statistics")
    ResponseEntity<ByteArrayResource> getStatistics(Principal principal) throws IOException {
        User user = userService.findUserByUsername(principal.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Flow required to write a PDF file to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        statisticService.createStatisticPdf(LocaleContextHolder.getLocale(), user, byteArrayOutputStream, user.getTrips());

        ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=statistics.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    /**
     * Updates the trip with the specified ID if the authenticated user has 'TOURMANAGER' authority.
     *
     * @param id            Identifier of the trip to be updated
     * @param tripDto       A {@link CreateTripDto} object containing updated trip information
     * @param bindingResult The {@link BindingResult} object to check out
     * @return The URL redirects to the updated trip page if the update was successful, otherwise the edit_tour view name if there are validation errors.
     */
    @PreAuthorize("hasAuthority('TOURMANAGER')")
    @PostMapping("/tours/{id}/edit")
    String editTrip(@PathVariable String id, @ModelAttribute("trip") CreateTripDto tripDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "edit_tour";

        tripService.updateTrip(tripDto);

        return "redirect:/tours/" + id;
    }

    /**
     * This function is responsible for purchasing a trip.
     *
     * @param id The ID of the trip being purchased.
     * @throws IllegalStateException    If the trip is not available for purchase.
     * @throws IllegalArgumentException If the trip ID provided is invalid.
     * @throws NullPointerException     If the trip ID is {@code null}.
     */
    @PostMapping("/tours/{id}/buy")
    String buyTrip(@PathVariable String id, Model model, Principal principal) {
        Optional<Trip> trip = tripService.findById(Long.parseLong(id));

        if (trip.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found");

        Optional<User> user = userService.findUserByUsername(principal.getName());

        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        if (ticketRepository.existsTicketByUserAndTrip(user.get(), trip.get())) {
            model.addAttribute("tripIsAlreadyOrdered", true);

            return "redirect:/tours/" + id + "?isBought=true";
        }

        ticketRepository.save(new Ticket(user.get(), trip.get()));

        return "redirect:/thanks_for_buying";
    }

    /**
     * Adds a review of the trip.
     *
     * @param id              Trip ID
     * @param reviewSubmitDTO Submission of a DTO recall
     * @param bindingResult   Binding result for verification
     * @param principal       An object representing an authenticated user
     * @return Redirect URL after adding feedback
     */
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/tours/{id}/reviews/add")
    String addReview(@PathVariable long id, @ModelAttribute("review") @Valid ReviewSubmitDto reviewSubmitDTO, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors())
            return "redirect:/tours/" + id;

        Optional<User> userDto = userService.findUserByUsername(principal.getName());
        Optional<Trip> trip = tripService.findById(id);

        if (userDto.isEmpty() || trip.isEmpty())
            return "redirect:/tours/" + id;

        Review review = new Review();
        review.setText(reviewSubmitDTO.getText());
        review.setUser(userDto.get());
        review.setTrip(trip.get());
        review.setDate(new Timestamp(System.currentTimeMillis()));

        reviewService.saveReview(review);

        return "redirect:/tours/" + id;
    }

    /**
     * Returns the view for adding a new trip.
     *
     * @param model Representation model
     * @return Submission URL for adding a new trip
     */
    @PreAuthorize("hasAuthority('TOURMANAGER')")
    @GetMapping("/tours/add")
    String addTrip(Model model) {
        model.addAttribute("trip", new CreateTripDto());

        return "add_tour";
    }

    /**
     * Adds a new trip to the database for the tour manager.
     *
     * @param tripDto       {@link CreateTripDto} object containing trip information
     * @param bindingResult Object {@link BindingResult} for checking
     * @param principal     {@link Principal} object representing an authenticated user
     * @return URL redirects to the newly created travel page
     */
    @PreAuthorize("hasAuthority('TOURMANAGER')")
    @PostMapping("/tours/add")
    String addTripPost(@ModelAttribute("trip") @Valid CreateTripDto tripDto, BindingResult bindingResult, Principal principal) {
        // Checking for errors in the form
        if (bindingResult.hasErrors())
            return "add_tour";

        // Get user object by user name
        String username = principal.getName();
        Optional<User> userDto = userService.findUserByUsername(username);

        // Check that the user exists, otherwise return the page with added trip
        if (userDto.isEmpty())
            return "add_tour";

        // Creating a trip based on the data sent from the form
        Trip trip = new Trip(tripDto);
        trip.setUser(userDto.get());

        // Saving the trip to the database
        tripService.saveTrip(trip, tripDto.getImages());

        // Redirects to the created trip page
        return "redirect:/tours/" + trip.getId();
    }

    /**
     * Returns reviews for a trip with the specified identifier.
     *
     * @param id             Trip ID
     * @param page           Page number
     * @param elementsInPage Number of elements on the page
     * @return {@link ResponseEntity} with a {@link ReviewsDto} object containing reviews and metadata
     */
    @GetMapping("/api/tours/{id}/reviews")
    @ResponseBody
    ResponseEntity<ReviewsDto> getReviews(@PathVariable long id, @RequestParam int page, @RequestParam int elementsInPage) {
        List<ReviewDto> reviews = new ArrayList<>();

        ReviewsDto reviewsDto = new ReviewsDto();
        reviewsDto.setCurrentPage(page);
        reviewsDto.setElementsOnPage(elementsInPage);

        Page<Review> reviewsPage = reviewService.findReviewsByTripId(id, PageRequest.of(page, elementsInPage));

        reviewsDto.setTotalElements(reviewsPage.getTotalElements());
        reviewsDto.setTotalPages(reviewsPage.getTotalPages());

        for (Review review : reviewsPage)
            reviews.add(new ReviewDto(review));

        reviewsDto.setReviews(reviews);

        return new ResponseEntity<>(reviewsDto, HttpStatus.OK);
    }

    /**
     * Retrieves trip data by identifier
     *
     * @param id Trip ID
     * @return {@link ResponseEntity} with the {@link TripDto} object.
     */
    @GetMapping("/api/tours/{id}")
    @ResponseBody
    ResponseEntity<TripDto> getTrip(@PathVariable String id) {
        Optional<Trip> trip = tripService.findById(Long.parseLong(id));

        if (trip.isPresent()) {
            TripDto tripDto = new TripDto(trip.get());
            tripDto.setPrice(exchangeRateService.convertCurrency(trip.get().getPrice()));

            return new ResponseEntity<>(new TripDto(trip.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Gets a list of vouchers
     *
     * @param page           Page number
     * @param elementsInPage Number of elements on one page
     * @return List of trips
     */
    @GetMapping("/api/tours")
    @ResponseBody
    TripsDto getAllTrips(@RequestParam int page,
                         @RequestParam int elementsInPage,
                         @RequestParam(defaultValue = "0") Long minPrice,
                         @RequestParam(defaultValue = "999999") Long maxPrice) {
        List<TripReducedDto> trips = new ArrayList<>();

        Page<Trip> tripsPage;

        tripsPage = tripService.findAllTripsByPrice(minPrice, maxPrice, PageRequest.of(page, elementsInPage));

        return getTripsDto(page, elementsInPage, tripsPage, trips);
    }

    /**
     * Gets a list of trips for the tour manager.
     *
     * @param page           Results Page Number.
     * @param elementsInPage The number of elements displayed on the page.
     * @param principal      A {@link Principal} object containing the username of the authenticated tour manager.
     * @return A {@link TripDto} object containing a list of trips for the tour manager.
     */
    @PreAuthorize("hasAuthority('TOURMANAGER')")
    @GetMapping("/api/tours/tourmanager")
    @ResponseBody
    TripsDto getTripsForManager(@RequestParam int page, @RequestParam int elementsInPage, Principal principal) {
        List<TripReducedDto> trips = new ArrayList<>();

        Page<Trip> tripsPage = tripService.findTripsByUsername(principal.getName(), PageRequest.of(page, elementsInPage));

        return getTripsDto(page, elementsInPage, tripsPage, trips);
    }

    /**
     * Receives a list of vouchers purchased.
     *
     * @param page           Results Page Number.
     * @param elementsInPage The number of elements displayed on the page.
     * @param principal      A {@link Principal} object containing the username of the authenticated tour manager.
     * @return A {@link TripDto} object containing a list of purchased trips.
     */
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
            tripReducedDto.setPrice(exchangeRateService.convertCurrency(trip.getPrice()));

            trips.add(tripReducedDto);
        }

        tripsDto.setTrips(trips);
        return tripsDto;
    }
}
