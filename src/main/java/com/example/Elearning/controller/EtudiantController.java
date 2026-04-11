package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EtudiantController {

    @GetMapping("/etudiant/dashboard")
    public String etudiantDashboard() {
        return "etudiant/dashboard";
    }
}
