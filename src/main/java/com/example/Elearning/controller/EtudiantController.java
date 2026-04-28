package com.example.Elearning.controller;

import com.example.Elearning.Config.CustomUserDetails;
import com.example.Elearning.Entity.Document;
import com.example.Elearning.Entity.Matiere;
import com.example.Elearning.Entity.ModuleCours;
import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.DocumentRepository;
import com.example.Elearning.Repository.ModuleCoursRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class EtudiantController {

    @GetMapping("/etudiant/dashboard")
    public String etudiantDashboard() {
        return "etudiant/dashboard";
    }

    private final ModuleCoursRepository moduleCoursRepository;
    private final DocumentRepository documentRepository;

    public EtudiantController(ModuleCoursRepository moduleCoursRepository,
                              DocumentRepository documentRepository) {
        this.moduleCoursRepository = moduleCoursRepository;
        this.documentRepository = documentRepository;
    }

    @GetMapping("/etudiant/matieres")
    public String mesMatieres(Authentication authentication, Model model) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Utilisateur etudiant = userDetails.getUtilisateur();

        List<Matiere> matieres = moduleCoursRepository.findMatieresForStudent(
                etudiant.getNiveau().getId(),
                etudiant.getFiliere().getId()
        );

        model.addAttribute("matieres", matieres);

        return "etudiant/matieres";
    }

    @GetMapping("/etudiant/matieres/{id}/modules")
    public String modulesParMatiere(@PathVariable Long id,
                                    Authentication authentication,
                                    Model model) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Utilisateur etudiant = userDetails.getUtilisateur();

        List<ModuleCours> modules = moduleCoursRepository.findModulesForStudentByMatiere(
                id,
                etudiant.getNiveau().getId(),
                etudiant.getFiliere().getId()
        );

        model.addAttribute("modules", modules);

        return "etudiant/modules";
    }

    @GetMapping("/etudiant/modules/{id}/contenu")
    public String contenuModule(@PathVariable Long id,
                                Authentication authentication,
                                Model model) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Utilisateur etudiant = userDetails.getUtilisateur();

        ModuleCours module = moduleCoursRepository.findModuleForStudent(
                id,
                etudiant.getNiveau().getId(),
                etudiant.getFiliere().getId()
        ).orElseThrow(() -> new RuntimeException("Accès refusé"));

        List<Document> documents = documentRepository.findByModuleId(id);

        model.addAttribute("module", module);
        model.addAttribute("documents", documents);

        return "etudiant/contenu";
    }
}
