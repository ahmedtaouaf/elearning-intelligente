package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Layouts {
    @GetMapping("/layouts/boxed")
    public String boxed() {
        return "layouts-eg/boxed";
    }

    @GetMapping("/layouts/compact")
    public String compact() {
        return "layouts-eg/compact";
    }    

    @GetMapping("/layouts/horizontal")
    public String horizontal() {
        return "layouts-eg/horizontal";
    }

    @GetMapping("/layouts/preloader")
    public String preloader() {
        return "layouts-eg/preloader";
    }

    @GetMapping("/layouts/scrollable")
    public String scrollable() {
        return "layouts-eg/scrollable";
    }
}