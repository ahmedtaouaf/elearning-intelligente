package com.example.Elearning.controller;

import com.example.Elearning.Config.CustomUserDetails;
import com.example.Elearning.Dto.AIPreviewData;
import com.example.Elearning.Entity.Document;
import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.DocumentRepository;
import com.example.Elearning.Service.DocumentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentRepository documentRepository;


    public DocumentController(DocumentService documentService, DocumentRepository documentRepository) {
        this.documentService = documentService;
        this.documentRepository = documentRepository;
    }

    @PostMapping("/enseignant/documents/upload")
    public String uploadDocument(@RequestParam String titre,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam Long moduleId,
                                 @RequestParam String modeUpload,
                                 @RequestParam(required = false) String aiType,
                                 Authentication authentication,
                                 HttpSession session) {

        try {
            CustomUserDetails userDetails =
                    (CustomUserDetails) authentication.getPrincipal();

            Utilisateur user = userDetails.getUtilisateur();

            if ("AI".equalsIgnoreCase(modeUpload)) {
                AIPreviewData previewData = documentService.prepareAiPreview(
                        titre,
                        file,
                        moduleId,
                        aiType,
                        user
                );

                session.setAttribute("AI_PREVIEW_DATA", previewData);
                return "redirect:/enseignant/documents/preview";
            } else {
                documentService.uploadStandardDocument(
                        titre,
                        file,
                        moduleId,
                        user
                );

                return "redirect:/enseignant/modules/" + moduleId;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/enseignant/modules/" + moduleId;
        }
    }

    @GetMapping("/enseignant/documents/preview")
    public String previewGeneratedDocument(HttpSession session, Model model) {
        AIPreviewData previewData =
                (AIPreviewData) session.getAttribute("AI_PREVIEW_DATA");

        if (previewData == null) {
            return "redirect:/enseignant/matieres";
        }

        Path previewFileName = Paths.get(previewData.getPreviewPdfPath()).getFileName();

        model.addAttribute("titre", previewData.getTitre());
        model.addAttribute("aiType", previewData.getAiType());
        model.addAttribute("moduleId", previewData.getModuleId());
        model.addAttribute("pdfUrl", "/temp-preview/" + previewFileName);

        return "enseignant/ai-preview";
    }

    @PostMapping("/enseignant/documents/preview/confirm")
    public String confirmGeneratedDocument(Authentication authentication,
                                           HttpSession session) {
        try {
            CustomUserDetails userDetails =
                    (CustomUserDetails) authentication.getPrincipal();

            Utilisateur user = userDetails.getUtilisateur();

            AIPreviewData previewData =
                    (AIPreviewData) session.getAttribute("AI_PREVIEW_DATA");

            if (previewData == null) {
                return "redirect:/enseignant/matieres";
            }

            Long moduleId = previewData.getModuleId();

            documentService.confirmAiPreview(previewData, user);
            session.removeAttribute("AI_PREVIEW_DATA");

            return "redirect:/enseignant/modules/" + moduleId;

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/enseignant/matieres";
        }
    }

    @PostMapping("/enseignant/documents/preview/cancel")
    public String cancelGeneratedDocument(Authentication authentication,
                                          HttpSession session) {
        try {
            CustomUserDetails userDetails =
                    (CustomUserDetails) authentication.getPrincipal();

            Utilisateur user = userDetails.getUtilisateur();

            AIPreviewData previewData =
                    (AIPreviewData) session.getAttribute("AI_PREVIEW_DATA");

            Long moduleId = previewData != null ? previewData.getModuleId() : null;

            documentService.cancelAiPreview(previewData, user);
            session.removeAttribute("AI_PREVIEW_DATA");

            if (moduleId != null) {
                return "redirect:/enseignant/modules/" + moduleId;
            }
            return "redirect:/enseignant/matieres";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/enseignant/matieres";
        }
    }

    @GetMapping("/documents/view/{id}")
    public String viewDocument(@PathVariable Long id, Model model) {
        Document document = documentRepository.findById(id).orElseThrow();

        model.addAttribute("titre", document.getTitre());
        model.addAttribute("pdfUrl", "/uploads/" + document.getNomFichier());

        return "enseignant/pdf-viewer";
    }

    @GetMapping("/documents/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) throws Exception {
        Document document = documentRepository.findById(id).orElseThrow();

        Path path = Paths.get(document.getCheminFichier());
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + document.getNomFichier() + "\"")
                .body(resource);
    }

    @GetMapping("/enseignant/documents/delete/{id}")
    public String deleteDocument(@PathVariable Long id,
                                 @RequestParam Long moduleId,
                                 Authentication authentication) {

        try {
            CustomUserDetails userDetails =
                    (CustomUserDetails) authentication.getPrincipal();

            Utilisateur user = userDetails.getUtilisateur();

            documentService.deleteDocument(id, user);

            return "redirect:/enseignant/modules/" + moduleId;

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/enseignant/modules/" + moduleId;
        }
    }
}
