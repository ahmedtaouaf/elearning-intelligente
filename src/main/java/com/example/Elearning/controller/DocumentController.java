package com.example.Elearning.controller;

import com.example.Elearning.Config.CustomUserDetails;
import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Service.DocumentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/enseignant/documents/upload")
    public String uploadDocument(@RequestParam String titre,
                                 @RequestParam String typeFichier,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam Long moduleId,
                                 Authentication authentication) {

        try {
            CustomUserDetails userDetails =
                    (CustomUserDetails) authentication.getPrincipal();

            Utilisateur user = userDetails.getUtilisateur();

            documentService.uploadDocument(
                    titre,
                    typeFichier,
                    file,
                    moduleId,
                    user
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/enseignant/modules/" + moduleId;
    }
}
