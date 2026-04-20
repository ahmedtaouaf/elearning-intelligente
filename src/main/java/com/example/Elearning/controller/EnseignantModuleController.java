package com.example.Elearning.controller;

import com.example.Elearning.Config.CustomUserDetails;
import com.example.Elearning.Entity.Document;
import com.example.Elearning.Entity.Matiere;
import com.example.Elearning.Entity.ModuleCours;
import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.DocumentRepository;
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
    private final DocumentRepository documentRepository;

    public EnseignantModuleController(ModuleCoursRepository moduleCoursRepository, MatiereRepository matiereRepository, DocumentRepository documentRepository) {
        this.moduleCoursRepository = moduleCoursRepository;
        this.matiereRepository = matiereRepository;
        this.documentRepository = documentRepository;
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

    @GetMapping("/enseignant/modules/{id}")
    public String contenuModule(@PathVariable Long id,
                                Authentication authentication,
                                Model model) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Utilisateur user = userDetails.getUtilisateur();

        ModuleCours module = moduleCoursRepository.findById(id)
                .orElseThrow();

        // 🔐 sécurité : vérifier que ce module appartient à l'enseignant
        if (!module.getMatiere().getEnseignant().getId().equals(user.getId())) {
            throw new RuntimeException("Accès refusé !");
        }

        model.addAttribute("module", module);
        model.addAttribute("documents", module.getDocuments());

        return "enseignant/module-details";
    }

    /*@GetMapping("/documents/view/{id}")
    public String viewDocument(@PathVariable Long id, Model model) {
        Document document = documentRepository.findById(id).orElseThrow();

        model.addAttribute("pdfUrl", "/uploads/" + document.getNomFichier());
        model.addAttribute("titre", document.getTitre());

        return "enseignant/pdf-viewer";
    }*/

}
