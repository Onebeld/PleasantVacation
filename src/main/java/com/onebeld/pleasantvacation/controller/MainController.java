package com.onebeld.pleasantvacation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping
    public String home() {
        return "index";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/hub")
    public String hub() {
        return "hub";
    }
}
