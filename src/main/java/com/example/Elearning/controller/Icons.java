package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Icons {
    @GetMapping("/icons/flags")
    public String flags() {
        return "icons/flags";
    }

    @GetMapping("/icons/lucide")
    public String lucide() {
        return "icons/lucide";
    }

    @GetMapping("/icons/tabler")
    public String tabler() {
        return "icons/tabler";
    }
}