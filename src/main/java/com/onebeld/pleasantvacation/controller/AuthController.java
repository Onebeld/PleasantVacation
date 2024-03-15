package com.onebeld.pleasantvacation.controller;

import com.onebeld.pleasantvacation.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class AuthController {
    @RequestMapping
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);

        return "register";
    }

    @PostMapping("/add/user")
    public ResponseEntity addUser(@RequestBody UserDto userDto) {
        // TODO: Add user

        return ResponseEntity.ok(HttpStatus.NOT_IMPLEMENTED);
    }
}
