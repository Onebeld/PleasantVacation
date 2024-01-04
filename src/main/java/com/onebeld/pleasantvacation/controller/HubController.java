package com.onebeld.pleasantvacation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hub")
public class HubController {
    @RequestMapping
    public String hub() {
        return "hub";
    }
}
