package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Pages {
    @GetMapping("/pages/coming-soon")
    public String comingSoon() {
        return "pages/coming-soon";
    }

    @GetMapping("/pages/empty")
    public String empty() {
        return "pages/empty";
    }

    @GetMapping("/pages/faq")
    public String faq() {
        return "pages/faq";
    }

    @GetMapping("/pages/pricing")
    public String pricing() {
        return "pages/pricing";
    }

    @GetMapping("/pages/search-results")
    public String searchResults() {
        return "pages/search-results";  
    }

    @GetMapping("/pages/sitemap")
    public String sitemap() {
        return "pages/sitemap";
    }

    @GetMapping("/pages/terms-conditions")
    public String termsConditions() {
        return "pages/terms-conditions";
    }

    @GetMapping("/pages/timeline")
    public String timeline() {
        return "pages/timeline";
    }
}