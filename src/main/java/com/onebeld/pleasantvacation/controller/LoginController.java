package com.onebeld.pleasantvacation.controller;

import com.onebeld.pleasantvacation.dto.user.UserLoginDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@PreAuthorize("!isAuthenticated()")
@Controller
public class LoginController {
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginForm", new UserLoginDto());

        return "login";
    }
}
