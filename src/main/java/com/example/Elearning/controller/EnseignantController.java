package com.example.Elearning.controller;

import com.example.Elearning.Config.CustomUserDetails;
import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EnseignantController {

    private final UtilisateurRepository utilisateurRepository;
    private final MatiereRepository matiereRepository;
    private final ModuleCoursRepository moduleCoursRepository;
    private final DocumentRepository documentRepository;

    public EnseignantController(UtilisateurRepository utilisateurRepository,
                               MatiereRepository matiereRepository,
                               ModuleCoursRepository moduleCoursRepository,
                               DocumentRepository documentRepository,
                               ContenuGenereRepository contenuGenereRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.matiereRepository = matiereRepository;
        this.moduleCoursRepository = moduleCoursRepository;
        this.documentRepository = documentRepository;
    }

    @GetMapping("/enseignant/dashboard")
    public String enseignantDashboard(Authentication authentication, Model model) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Utilisateur enseignant = utilisateurRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();

        Long id = enseignant.getId();

        model.addAttribute("totalMatieres", matiereRepository.countByEnseignantId(id));
        model.addAttribute("totalModules", moduleCoursRepository.countByMatiereEnseignantId(id));
        model.addAttribute("totalDocuments", documentRepository.countByEnseignantId(id));
        model.addAttribute("totalAiDocuments", documentRepository.countByEnseignantIdAndModeUpload(id, "AI"));
        model.addAttribute("totalQcm", documentRepository.countByEnseignantIdAndTypeFichier(id, "QCM"));
        model.addAttribute("totalResume", documentRepository.countByEnseignantIdAndTypeFichier(id, "RESUME"));
        model.addAttribute("recentDocuments", documentRepository.findTop5ByEnseignantIdOrderByDateUploadDesc(id));

        model.addAttribute("teacherModeLabels", List.of("Standard", "IA"));
        model.addAttribute("teacherModeData", List.of(
                documentRepository.countTeacherDocumentsByMode(id, "MANUEL"),
                documentRepository.countTeacherDocumentsByMode(id, "AI")
        ));

        model.addAttribute("teacherTypeLabels", List.of("PDF", "QCM", "Résumé", "Examen"));
        model.addAttribute("teacherTypeData", List.of(
                documentRepository.countTeacherDocumentsByType(id, "PDF"),
                documentRepository.countTeacherDocumentsByType(id, "QCM"),
                documentRepository.countTeacherDocumentsByType(id, "RESUME"),
                documentRepository.countTeacherDocumentsByType(id, "EXAMEN")
        ));

        return "enseignant/dashboard";
    }

}
