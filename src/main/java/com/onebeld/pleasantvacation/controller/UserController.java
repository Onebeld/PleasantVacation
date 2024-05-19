package com.onebeld.pleasantvacation.controller;

import com.onebeld.pleasantvacation.dto.user.UserDto;
import com.onebeld.pleasantvacation.service.impl.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@PreAuthorize("hasAnyAuthority('USER', 'TOURMANAGER')")
@Controller
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping("/me")
    public String me(Model model, Principal principal) {
        UserDto user = userService.getUserByUsername(principal.getName());

        model.addAttribute("user", user);

        return "me";
    }
}
