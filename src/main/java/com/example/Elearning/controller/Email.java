package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Email {
    @GetMapping("/email/index")
    public String email() {
        return "email/index";
    }

    @GetMapping("/email/compose")
    public String compose() {
        return "email/compose";
    }

    @GetMapping("/email/details")
    public String details() {
        return "email/details";
    }
    
    @GetMapping("/email/templates")
    public String templates() {
        return "email/templates";
    }
}