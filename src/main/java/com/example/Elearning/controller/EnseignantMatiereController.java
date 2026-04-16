package com.example.Elearning.controller;

import com.example.Elearning.Config.CustomUserDetails;
import com.example.Elearning.Entity.Matiere;
import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.MatiereRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EnseignantMatiereController {

    private final MatiereRepository matiereRepository;

    public EnseignantMatiereController(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    @GetMapping("/enseignant/matieres")
    public String mesMatieres(Authentication authentication, Model model) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Utilisateur user = userDetails.getUtilisateur();

        // 🔥 récupérer uniquement ses matières
        List<Matiere> matieres =
                matiereRepository.findByEnseignantId(user.getId());

        model.addAttribute("matieres", matieres);

        return "enseignant/matieres"; // 🔥 très important
    }
}
