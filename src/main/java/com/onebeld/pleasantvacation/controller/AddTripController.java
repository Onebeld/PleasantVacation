package com.onebeld.pleasantvacation.controller;

import com.onebeld.pleasantvacation.dto.trip.TripDto;
import com.onebeld.pleasantvacation.entity.Trip;
import com.onebeld.pleasantvacation.entity.User;
import com.onebeld.pleasantvacation.entity.enums.TripState;
import com.onebeld.pleasantvacation.service.impl.TripServiceImpl;
import com.onebeld.pleasantvacation.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AddTripController {
    private final TripServiceImpl tripService;
    private final UserServiceImpl userService;

    public AddTripController(TripServiceImpl tripService, UserServiceImpl userService) {
        this.tripService = tripService;
        this.userService = userService;
    }

    @GetMapping("/tours/add")
    public String addTrip(Model model) {
        model.addAttribute("trip", new TripDto());

        return "add_tour";
    }

    @PostMapping("/tours/add")
    public String addTripPost(@ModelAttribute("trip") @Valid TripDto tripDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "add_tour";
        }

        // Get user from principal
        String username = principal.getName();
        Optional<User> userDto = userService.findUserByUsername(username);

        if (userDto.isEmpty()) {
            return "add_tour";
        }

        Trip trip = new Trip(tripDto);
        trip.setUser(userDto.get());
        trip.setState(TripState.ACTIVE);

        tripService.saveTrip(trip);

        return "redirect:/tours/" + trip.getId();
    }
}
