package com.onebeld.pleasantvacation.controllers;

import com.onebeld.pleasantvacation.dtos.user.UserLoginDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for logging into the system.
 */
@PreAuthorize("!isAuthenticated()")
@Controller
public class LoginController {
    /**
     * Gets the login page and adds a login form to the model.
     *
     * @param model The model used to transfer data to the view
     * @return Submission name for logging in
     */
    @GetMapping("/login")
    String getLoginPage(Model model) {
        model.addAttribute("loginForm", new UserLoginDto());

        return "login";
    }
}
