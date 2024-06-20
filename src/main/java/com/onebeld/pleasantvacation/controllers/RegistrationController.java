package com.onebeld.pleasantvacation.controllers;

import com.onebeld.pleasantvacation.dtos.user.UserDto;
import com.onebeld.pleasantvacation.services.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller to register a new user.
 */
@PreAuthorize("!isAuthenticated()")
@Controller
public class RegistrationController {
    private final UserServiceImpl userService;

    public RegistrationController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * Gets the registration page and adds a new {@link UserDto} object to the model.
     *
     * @param model A model object used to pass data to the view
     * @return Name of the registration submission
     */
    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("userForm", new UserDto());

        return "registration";
    }

    /**
     * Adds a new user to the system.
     *
     * @param userDto       A user data transfer object containing information about the user
     * @param bindingResult The object containing the results of the {@link UserDto} validation.
     * @param model         A model object used to pass data to the view
     * @return The name of the view that will be displayed when the user is added
     */
    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "registration";

        boolean result = false;

        if (userDto.getRole().equals("USER"))
            result = userService.saveUser(userDto);
        else if (userDto.getRole().equals("TOURMANAGER"))
            result = userService.saveTourmanager(userDto);

        if (!result) {
            model.addAttribute("usernameError", "User exists");

            return "registration";
        }

        return "redirect:/";
    }
}
