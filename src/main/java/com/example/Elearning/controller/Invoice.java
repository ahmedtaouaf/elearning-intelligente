package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Invoice {
    @GetMapping("/invoice/index")
    public String invoice() {
        return "invoice/index";
    }

    @GetMapping("/invoice/create")
    public String create() {
        return "invoice/create";
    }

    @GetMapping("/invoice/details")
    public String details() {
        return "invoice/details";
    }
}