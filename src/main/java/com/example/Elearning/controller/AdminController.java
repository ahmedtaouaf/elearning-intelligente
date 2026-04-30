package com.example.Elearning.controller;

import com.example.Elearning.Repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final UtilisateurRepository utilisateurRepository;
    private final MatiereRepository matiereRepository;
    private final ModuleCoursRepository moduleCoursRepository;
    private final DocumentRepository documentRepository;
    private final ContenuGenereRepository contenuGenereRepository;

    public AdminController(UtilisateurRepository utilisateurRepository,
                               MatiereRepository matiereRepository,
                               ModuleCoursRepository moduleCoursRepository,
                               DocumentRepository documentRepository,
                               ContenuGenereRepository contenuGenereRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.matiereRepository = matiereRepository;
        this.moduleCoursRepository = moduleCoursRepository;
        this.documentRepository = documentRepository;
        this.contenuGenereRepository = contenuGenereRepository;
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalUsers", utilisateurRepository.count());
        model.addAttribute("totalTeachers", utilisateurRepository.countByRoleNom("ENSEIGNANT"));
        model.addAttribute("totalStudents", utilisateurRepository.countByRoleNom("ETUDIANT"));
        model.addAttribute("totalMatieres", matiereRepository.count());
        model.addAttribute("totalModules", moduleCoursRepository.count());
        model.addAttribute("totalDocuments", documentRepository.count());
        model.addAttribute("totalAiDocuments", documentRepository.countByModeUpload("AI"));
        model.addAttribute("totalGeneratedContents", contenuGenereRepository.count());
        model.addAttribute("recentDocuments", documentRepository.findTop5ByOrderByDateUploadDesc());
        return "admin/dashboard";
    }


}
