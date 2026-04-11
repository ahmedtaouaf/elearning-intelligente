package com.example.Elearning.controller;

import com.example.Elearning.Entity.Role;
import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.RoleRepository;
import com.example.Elearning.Service.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/utilisateurs")
public class AdminUtilisateurController {

    private final UtilisateurService utilisateurService;
    private final RoleRepository roleRepository;

    public AdminUtilisateurController(UtilisateurService utilisateurService,
                                      RoleRepository roleRepository) {
        this.utilisateurService = utilisateurService;
        this.roleRepository = roleRepository;
    }

    // 📋 LISTE
    @GetMapping
    public String list(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.getAll());
        return "admin/utilisateurs/list";
    }


    // ➕ FORM ADD
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/utilisateurs/form";
    }

    // 💾 SAVE
    @PostMapping("/save")
    public String save(@ModelAttribute Utilisateur utilisateur) {

        Role role = roleRepository.findById(utilisateur.getRole().getId()).orElseThrow();
        utilisateur.setRole(role);

        utilisateurService.save(utilisateur);

        return "redirect:/admin/utilisateurs";
    }

    // ✏️ EDIT
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("utilisateur", utilisateurService.getById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/utilisateurs/form";
    }

    // ❌ DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        utilisateurService.delete(id);
        return "redirect:/admin/utilisateurs";
    }
}