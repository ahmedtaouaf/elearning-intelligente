package com.example.Elearning.controller;

import com.example.Elearning.Config.CustomUserDetails;
import com.example.Elearning.Entity.Matiere;
import com.example.Elearning.Entity.ModuleCours;
import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.MatiereRepository;
import com.example.Elearning.Repository.ModuleCoursRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class EnseignantModuleController {

    private final ModuleCoursRepository moduleCoursRepository;
    private final MatiereRepository matiereRepository;

    public EnseignantModuleController(ModuleCoursRepository moduleCoursRepository, MatiereRepository matiereRepository) {
        this.moduleCoursRepository = moduleCoursRepository;
        this.matiereRepository = matiereRepository;
    }

    @GetMapping("/enseignant/matieres/{id}/modules")
    public String modulesParMatiere(@PathVariable Long id,
                                    Authentication authentication,
                                    Model model) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Utilisateur user = userDetails.getUtilisateur();

        Matiere matiere = matiereRepository.findById(id).orElseThrow();

        // 🔐 sécurité
        if (!matiere.getEnseignant().getId().equals(user.getId())) {
            throw new RuntimeException("Accès refusé");
        }

        List<ModuleCours> modules =
                moduleCoursRepository.findByMatiereId(id);

        model.addAttribute("modules", modules);
        model.addAttribute("matiere", matiere);

        return "enseignant/modules";
    }
}
