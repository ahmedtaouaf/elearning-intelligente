package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Forms {
    @GetMapping("/forms/elements")
    public String elements() {
        return "forms/elements";
    }

    @GetMapping("/forms/fileuploads")
    public String fileuploads() {
        return "forms/fileuploads";
    }

    @GetMapping("/forms/layouts")
    public String layouts() {
        return "forms/layouts";
    }

    @GetMapping("/forms/other-plugins")
    public String otherPlugins() {
        return "forms/other-plugins";
    }

    @GetMapping("/forms/pickers")
    public String pickers() {
        return "forms/pickers";
    }

    @GetMapping("/forms/range-slider")
    public String rangeSlider() {
        return "forms/range-slider";
    }

    @GetMapping("/forms/select")
    public String select() {
        return "forms/select";
    }

    @GetMapping("/forms/text-editors")
    public String textEditors() {
        return "forms/text-editors";
    }

    @GetMapping("/forms/validation")
    public String validation() {
        return "forms/validation";
    }

    @GetMapping("/forms/wizard")
    public String wizard() {
        return "forms/wizard";
    }
}