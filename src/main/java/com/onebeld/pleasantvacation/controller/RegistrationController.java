package com.onebeld.pleasantvacation.controller;

import com.onebeld.pleasantvacation.dto.user.UserDto;
import com.onebeld.pleasantvacation.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final UserServiceImpl userService;

    public RegistrationController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("userForm", new UserDto());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "registration";

        boolean result = false;

        if (userDto.getRole().equals("USER")) {
            result = userService.saveUser(userDto);
        }
        else if (userDto.getRole().equals("TOURMANAGER")) {
            result = userService.saveTourmanager(userDto);
        }

        if (!result) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/";
    }
}
