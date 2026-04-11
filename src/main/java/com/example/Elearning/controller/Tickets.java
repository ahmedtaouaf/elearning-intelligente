package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Tickets {
    @GetMapping("/tickets/list")
    public String ticketsList() {
        return "tickets/list";
    }

    @GetMapping("/tickets/details")
    public String ticketsDetails() {
        return "tickets/details";
    }

    @GetMapping("/tickets/create")
    public String ticketsCreate() {
        return "tickets/create";
    }
}