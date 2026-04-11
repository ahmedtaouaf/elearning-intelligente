package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Dashboards {
    @GetMapping("/")
    public String index() {
        return "dashboards/index";
    }

    @GetMapping("/dashboards/index-2")
    public String dashboardTwo() {
        return "dashboards/index-2";
    }
}