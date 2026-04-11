package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Widgets {
    @GetMapping("/widgets")
    public String index() {
        return "widgets/index";
    }
}