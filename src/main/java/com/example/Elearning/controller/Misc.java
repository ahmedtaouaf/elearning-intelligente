package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Misc {
    @GetMapping("/misc/clipboard")
    public String clipboard() {
        return "misc/clipboard";
    }

    @GetMapping("/misc/i18")
    public String i18() {
        return "misc/i18";
    }

    @GetMapping("/misc/pass-meter")
    public String passMeter() {
        return "misc/pass-meter";
    }

    @GetMapping("/misc/pdf-viewer")
    public String pdfViewer() {
        return "misc/pdf-viewer";
    }

    @GetMapping("/misc/sortable")
    public String sortable() {
        return "misc/sortable";
    }

    @GetMapping("/misc/sweet-alerts")
    public String sweetAlerts() {
        return "misc/sweet-alerts";
    }

    @GetMapping("/misc/tour")
    public String tour() {
        return "misc/tour";
    }

    @GetMapping("/misc/tree-view")
    public String treeView() {
        return "misc/tree-view";
    }
}