package com.onebeld.pleasantvacation.controller;

import com.onebeld.pleasantvacation.dto.trip.TripReducedDto;
import com.onebeld.pleasantvacation.dto.user.UserDto;
import com.onebeld.pleasantvacation.entity.Trip;
import com.onebeld.pleasantvacation.service.impl.TripServiceImpl;
import com.onebeld.pleasantvacation.service.impl.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final UserServiceImpl userService;
    private final TripServiceImpl tripService;

    public UserController(UserServiceImpl userService, TripServiceImpl tripService) {
        this.userService = userService;
        this.tripService = tripService;
    }

    @RequestMapping("/me")
    public String me(Model model, Principal principal) {
        UserDto user = userService.getUserByUsername(principal.getName());

        model.addAttribute("user", user);

        return "me";
    }

    @GetMapping("api/user/trips/")
    @ResponseBody
    List<TripReducedDto> getAllTrips(@RequestParam int page, @RequestParam int elementsInPage, Principal principal) {
        UserDto user = userService.getUserByUsername(principal.getName());

        List<TripReducedDto> trips = new ArrayList<>();

        Page<Trip> tripsPage;

        if (user.getRole() == "USER")
            tripsPage = tripService.findAllPurchasedTripsByUsername(user.getUsername(), PageRequest.of(page, elementsInPage));
        else
            tripsPage = tripService.findTripsByUsername(user.getUsername(), PageRequest.of(page, elementsInPage));

        for (Trip trip : tripsPage) {
            trips.add(new TripReducedDto(trip));
        }

        return trips;
    }
}
