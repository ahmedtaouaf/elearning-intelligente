package com.example.Elearning.controller;

import com.example.Elearning.Entity.Matiere;
import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.UtilisateurRepository;
import com.example.Elearning.Service.MatiereService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/matieres")
public class AdminMatiereController {

    private final MatiereService matiereService;
    private final UtilisateurRepository utilisateurRepository;

    public AdminMatiereController(MatiereService matiereService,
                                  UtilisateurRepository utilisateurRepository) {
        this.matiereService = matiereService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("matieres", matiereService.getAll());
        model.addAttribute("matiere", new Matiere());

        model.addAttribute("enseignants",
                utilisateurRepository.findAll().stream()
                        .filter(u -> u.getRole().getNom().equals("ENSEIGNANT"))
                        .toList());

        return "admin/matieres/list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Matiere matiere) {

        Utilisateur enseignant =
                utilisateurRepository.findById(matiere.getEnseignant().getId()).orElseThrow();

        matiere.setEnseignant(enseignant);

        matiereService.save(matiere);

        return "redirect:/admin/matieres";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        matiereService.delete(id);
        return "redirect:/admin/matieres";
    }
}
