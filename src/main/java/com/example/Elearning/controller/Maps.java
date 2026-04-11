package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Maps {
    @GetMapping("/maps/leaflet")
    public String leaflet() {
        return "maps/leaflet";
    }

    @GetMapping("/maps/vector")
    public String vector() {
        return "maps/vector";
    }
}