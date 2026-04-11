package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Crm {
    @GetMapping("/crm/activities")
    public String activities() {
        return "crm/activities";
    }

    @GetMapping("/crm/campaign")
    public String campaign() {
        return "crm/campaign";
    }

    @GetMapping("/crm/contacts")
    public String contacts() {
        return "crm/contacts";
    }

    @GetMapping("/crm/customers")
    public String customers() {
        return "crm/customers";
    }

    @GetMapping("/crm/deals")
    public String deals() {
        return "crm/deals";
    }

    @GetMapping("/crm/estimations")
    public String estimations() {
        return "crm/estimations";
    }

    @GetMapping("/crm/leads")
    public String leads() {
        return "crm/leads";
    }

    @GetMapping("/crm/opportunities")
    public String opportunities() {
        return "crm/opportunities";
    }

    @GetMapping("/crm/pipeline")
    public String pipeline() {
        return "crm/pipeline";
    }

    @GetMapping("/crm/proposals")
    public String proposals() {
        return "crm/proposals";
    }
}