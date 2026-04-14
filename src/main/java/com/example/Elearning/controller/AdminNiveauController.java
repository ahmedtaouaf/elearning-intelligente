package com.example.Elearning.controller;

import com.example.Elearning.Entity.Niveau;
import com.example.Elearning.Service.NiveauService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/niveaux")
public class AdminNiveauController {

    private final NiveauService niveauService;

    public AdminNiveauController(NiveauService niveauService) {
        this.niveauService = niveauService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("niveaux", niveauService.getAll());
        model.addAttribute("niveau", new Niveau());
        return "admin/niveaux/list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Niveau niveau) {
        niveauService.save(niveau);
        return "redirect:/admin/niveaux";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        niveauService.delete(id);
        return "redirect:/admin/niveaux";
    }
}
