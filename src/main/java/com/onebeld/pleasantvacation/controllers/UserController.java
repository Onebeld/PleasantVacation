package com.onebeld.pleasantvacation.controllers;

import com.onebeld.pleasantvacation.dtos.user.UserDto;
import com.onebeld.pleasantvacation.services.implementations.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * The class is responsible for handling requests related to the user's profile page.
 * The user must have USER or TOURMANAGER authorization to access the page.
 */
@PreAuthorize("hasAnyAuthority('USER', 'TOURMANAGER')")
@Controller
public class UserController {
    private final UserServiceImpl userService;

    /**
     * Creates a new instance of {@link UserController}.
     *
     * @param userService An instance of {@link UserServiceImpl} to be used by the controller.
     */
    UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * Gets the self page with user information and adds it to the model.
     *
     * @param model     Model for adding user information
     * @param principal Director representing the authenticated user
     * @return Title of the “I” page submission
     */
    @RequestMapping("/me")
    String getMePage(Model model, Principal principal) {
        UserDto user = userService.getUserByUsername(principal.getName());

        model.addAttribute("user", user);

        return "me";
    }
}
