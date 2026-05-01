package com.example.Elearning.controller;

import com.example.Elearning.Config.CustomUserDetails;
import com.example.Elearning.Entity.Document;
import com.example.Elearning.Entity.Matiere;
import com.example.Elearning.Entity.ModuleCours;
import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class EtudiantController {

    private final UtilisateurRepository utilisateurRepository;
    private final ModuleCoursRepository moduleCoursRepository;
    private final DocumentRepository documentRepository;

    public EtudiantController(UtilisateurRepository utilisateurRepository, ModuleCoursRepository moduleCoursRepository,
                              DocumentRepository documentRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.moduleCoursRepository = moduleCoursRepository;
        this.documentRepository = documentRepository;
    }
    @GetMapping("/etudiant/dashboard")
    public String etudiantDashboard(Authentication authentication, Model model) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Utilisateur etudiant = utilisateurRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();

        Long niveauId = etudiant.getNiveau().getId();
        Long filiereId = etudiant.getFiliere().getId();

        model.addAttribute("totalMatieres",
                moduleCoursRepository.findMatieresForStudent(niveauId, filiereId).size());

        model.addAttribute("totalModules",
                moduleCoursRepository.countModulesForStudent(niveauId, filiereId));

        model.addAttribute("totalDocuments",
                documentRepository.countDocumentsForStudent(niveauId, filiereId));

        model.addAttribute("totalAiDocuments",
                documentRepository.countDocumentsForStudentByMode(niveauId, filiereId, "AI"));

        model.addAttribute("recentDocuments",
                documentRepository.findDocumentsForStudent(niveauId, filiereId)
                        .stream()
                        .limit(5)
                        .toList());

        model.addAttribute("studentModeLabels", List.of("Standard", "IA"));
        model.addAttribute("studentModeData", List.of(
                documentRepository.countDocumentsForStudentByMode(niveauId, filiereId, "MANUEL"),
                documentRepository.countDocumentsForStudentByMode(niveauId, filiereId, "AI")
        ));

        model.addAttribute("studentTypeLabels", List.of("PDF", "QCM", "Résumé", "Examen"));
        model.addAttribute("studentTypeData", List.of(
                documentRepository.countDocumentsForStudentByType(niveauId, filiereId, "PDF"),
                documentRepository.countDocumentsForStudentByType(niveauId, filiereId, "QCM"),
                documentRepository.countDocumentsForStudentByType(niveauId, filiereId, "RESUME"),
                documentRepository.countDocumentsForStudentByType(niveauId, filiereId, "EXAMEN")
        ));

        return "etudiant/dashboard";
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
