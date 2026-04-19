package com.example.Elearning.Service;

import com.example.Elearning.Entity.Document;
import com.example.Elearning.Entity.ModuleCours;
import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.DocumentRepository;
import com.example.Elearning.Repository.ModuleCoursRepository;
import com.example.Elearning.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

@Service
public class DocumentService {

    @Value("${upload.dir}")
    private String uploadDir;

    private final DocumentRepository documentRepository;
    private final ModuleCoursRepository moduleCoursRepository;
    private final UtilisateurRepository utilisateurRepository;

    public DocumentService(DocumentRepository documentRepository,
                           ModuleCoursRepository moduleCoursRepository,
                           UtilisateurRepository utilisateurRepository) {
        this.documentRepository = documentRepository;
        this.moduleCoursRepository = moduleCoursRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public void uploadDocument(String titre,
                               MultipartFile file,
                               Long moduleId,
                               String modeUpload,
                               String aiType,
                               Utilisateur enseignant) throws IOException {

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        ModuleCours module = moduleCoursRepository.findById(moduleId)
                .orElseThrow();

        String finalModeUpload;
        String finalStatut;

        if ("AI".equalsIgnoreCase(modeUpload)) {
            finalModeUpload = (aiType != null && !aiType.isBlank()) ? aiType : "AI";
            finalStatut = "EN_ATTENTE";
        } else {
            finalModeUpload = "STANDARD";
            finalStatut = "VALIDE";
        }

        Document document = Document.builder()
                .titre(titre)
                .nomFichier(fileName)
                .cheminFichier(filePath.toString())
                .typeFichier("PDF")
                .dateUpload(LocalDate.now())
                .modeUpload(finalModeUpload)
                .statut(finalStatut)
                .enseignant(enseignant)
                .module(module)
                .build();

        documentRepository.save(document);
    }

    public void deleteDocument(Long documentId, Utilisateur enseignant) throws IOException {

        Document document = documentRepository.findById(documentId)
                .orElseThrow();

        // sécurité : vérifier que le document appartient bien à l'enseignant connecté
        if (!document.getEnseignant().getId().equals(enseignant.getId())) {
            throw new RuntimeException("Accès refusé : ce document n'appartient pas à cet enseignant.");
        }

        // supprimer le fichier physique si existe
        Path filePath = Paths.get(document.getCheminFichier());
        Files.deleteIfExists(filePath);

        // supprimer en base
        documentRepository.delete(document);
    }
}
