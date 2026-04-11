package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sidebar {
    @GetMapping("/sidebar/compact")
    public String compact() {
        return "sidebar/compact";
    }

    @GetMapping("/sidebar/dark")
    public String dark() {
        return "sidebar/dark";
    }

    @GetMapping("/sidebar/gradient")
    public String gradient() {
        return "sidebar/gradient";
    }

    @GetMapping("/sidebar/gray")
    public String gray() {
        return "sidebar/gray";
    }

    @GetMapping("/sidebar/icon-view")
    public String iconView() {
        return "sidebar/icon-view";
    }

    @GetMapping("/sidebar/image")
    public String image() {
        return "sidebar/image";
    }

    @GetMapping("/sidebar/no-icons")
    public String noIcons() {
        return "sidebar/no-icons";
    }

    @GetMapping("/sidebar/offcanvas")
    public String offcanvas() {
        return "sidebar/offcanvas";
    }

    @GetMapping("/sidebar/on-hover-active")
    public String onHoverActive() {
        return "sidebar/on-hover-active";
    }

    @GetMapping("/sidebar/on-hover")
    public String onHover() {
        return "sidebar/on-hover";
    }

    @GetMapping("/sidebar/with-lines")
    public String withLines() {
        return "sidebar/with-lines";
    }
}