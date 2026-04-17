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
                               String type,
                               MultipartFile file,
                               Long moduleId,
                               Utilisateur enseignant) throws IOException {

        // 🔥 créer dossier si n'existe pas
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 🔥 nom fichier unique
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(uploadDir, fileName);

        // 🔥 sauvegarde fichier
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        ModuleCours module = moduleCoursRepository.findById(moduleId)
                .orElseThrow();

        // 🔥 créer document
        Document document = Document.builder()
                .titre(titre)
                .nomFichier(file.getOriginalFilename())
                .cheminFichier(filePath.toString())
                .typeFichier(type)
                .dateUpload(LocalDate.now())
                .modeUpload("MANUEL")
                .statut("VALIDE")
                .enseignant(enseignant)
                .module(module)
                .build();

        documentRepository.save(document);
    }
}
