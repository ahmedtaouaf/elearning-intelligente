package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnseignantController {

    @GetMapping("/enseignant/dashboard")
    public String enseignantDashboard() {
        return "enseignant/dashboard";
    }
}
