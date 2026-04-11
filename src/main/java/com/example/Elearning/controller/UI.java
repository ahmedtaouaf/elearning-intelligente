package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UI {

    @GetMapping("/ui/accordions")
    public String accordions() {
        return "ui/accordions";
    }

    @GetMapping("/ui/alerts")
    public String alerts() {
        return "ui/alerts";
    }

    @GetMapping("/ui/badges")
    public String badges() {
        return "ui/badges";
    }

    @GetMapping("/ui/breadcrumb")
    public String breadcrumb() {
        return "ui/breadcrumb";
    }

    @GetMapping("/ui/buttons")
    public String buttons() {
        return "ui/buttons";
    }

    @GetMapping("/ui/cards")
    public String cards() {
        return "ui/cards";
    }

    @GetMapping("/ui/colors")
    public String colors() {
        return "ui/colors";
    }

    @GetMapping("/ui/collapse")
    public String collapse() {
        return "ui/collapse";
    }

    @GetMapping("/ui/carousel")
    public String carousel() {
        return "ui/carousel";
    }

    @GetMapping("/ui/dropdowns")
    public String dropdowns() {
        return "ui/dropdowns";
    }

    @GetMapping("/ui/grid")
    public String grid() {
        return "ui/grid";
    }

    @GetMapping("/ui/images")
    public String images() {
        return "ui/images";
    }

    @GetMapping("/ui/links")
    public String links() {
        return "ui/links";
    }

    @GetMapping("/ui/list-group")
    public String listGroup() {
        return "ui/list-group";
    }

    @GetMapping("/ui/modals")
    public String modals() {
        return "ui/modals";
    }
    
    @GetMapping("/ui/notifications")
    public String notifications() {
        return "ui/notifications";
    }

    @GetMapping("/ui/pagination")
    public String pagination() {
        return "ui/pagination";
    }

    @GetMapping("/ui/placeholders")
    public String placeholders() {
        return "ui/placeholders";
    }

    @GetMapping("/ui/offcanvas")
    public String offcanvas() {
        return "ui/offcanvas";
    }

    @GetMapping("/ui/popovers")
    public String popovers() {
        return "ui/popovers";
    }

    @GetMapping("/ui/progress")
    public String progress() {
        return "ui/progress";
    }

    @GetMapping("/ui/scrollspy")
    public String scrollSpy() {
        return "ui/scrollspy";
    }

    @GetMapping("/ui/spinners")
    public String spinners() {
        return "ui/spinners";
    }

    @GetMapping("/ui/tabs")
    public String tabs() {
        return "ui/tabs";
    }

    @GetMapping("/ui/tooltips")
    public String tooltips() {
        return "ui/tooltips";
    }

    @GetMapping("/ui/utilities")
    public String utilities() {
        return "ui/utilities";
    }     

    @GetMapping("/ui/videos")
    public String videos() {
        return "ui/videos";
    }       

    @GetMapping("/ui/typography")
    public String typography() {
        return "ui/typography";
    }            
}