package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Landing {
    @GetMapping("/landing")
    public String landing() {
        return "landing/index";
    }
}