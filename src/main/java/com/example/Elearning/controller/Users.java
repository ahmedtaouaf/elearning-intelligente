package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Users {
    @GetMapping("/users/contacts")
    public String contacts() {
        return "users/contacts";
    }

    @GetMapping("/users/permissions")
    public String permissions() {
        return "users/permissions";
    }

    @GetMapping("/users/profile")
    public String profile() {
        return "users/profile";
    }

    @GetMapping("/users/role-details")
    public String roleDetails() {
        return "users/role-details";
    }

    @GetMapping("/users/roles")
    public String roles() {
        return "users/roles";
    }
}