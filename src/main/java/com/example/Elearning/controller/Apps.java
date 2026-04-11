package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Apps {
    @GetMapping("/apps/api-keys")
    public String apiKeys() {
        return "apps/api-keys";
    }

    @GetMapping("/apps/calendar")
    public String calendar() {
        return "apps/calendar";
    }

    @GetMapping("/apps/chat")
    public String chat() {
        return "apps/chat";
    }

    @GetMapping("/apps/file-manager")
    public String fileManager() {
        return "apps/file-manager";
    }

    @GetMapping("/apps/social-feed")
    public String socialFeed() {
        return "apps/social-feed";
    }
}