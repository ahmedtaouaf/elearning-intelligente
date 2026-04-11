package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Topbar {
    @GetMapping("/topbar/gray")
    public String gray() {
        return "topbar/gray";
    }

    @GetMapping("/topbar/light")
    public String light() {
        return "topbar/light";
    }

    @GetMapping("/topbar/gradient")
    public String gradient() {
        return "topbar/gradient";
    }
}