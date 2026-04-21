package com.example.Elearning.Service;

import com.example.Elearning.Dto.AIPreviewData;
import com.example.Elearning.Entity.ContenuGenere;
import com.example.Elearning.Entity.Document;
import com.example.Elearning.Entity.ModuleCours;
import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.ContenuGenereRepository;
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

    @Value("${temp.dir}")
    private String tempDir;

    private final DocumentRepository documentRepository;
    private final ModuleCoursRepository moduleCoursRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ContenuGenereRepository contenuGenereRepository;
    private final PdfTextService pdfTextService;
    private final AiGenerationService aiGenerationService;
    private final PdfPreviewService pdfPreviewService;

    public DocumentService(DocumentRepository documentRepository,
                           ModuleCoursRepository moduleCoursRepository,
                           UtilisateurRepository utilisateurRepository,
                           ContenuGenereRepository contenuGenereRepository,
                           PdfTextService pdfTextService,
                           AiGenerationService aiGenerationService,
                           PdfPreviewService pdfPreviewService) {
        this.documentRepository = documentRepository;
        this.moduleCoursRepository = moduleCoursRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.contenuGenereRepository = contenuGenereRepository;
        this.pdfTextService = pdfTextService;
        this.aiGenerationService = aiGenerationService;
        this.pdfPreviewService = pdfPreviewService;
    }

    public void uploadStandardDocument(String titre,
                                       MultipartFile file,
                                       Long moduleId,
                                       Utilisateur enseignant) throws IOException {

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        ModuleCours module = moduleCoursRepository.findById(moduleId).orElseThrow();

        Document document = Document.builder()
                .titre(titre)
                .nomFichier(fileName)
                .cheminFichier(filePath.toString())
                .typeFichier("PDF")
                .dateUpload(LocalDate.now())
                .modeUpload("STANDARD")
                .statut("VALIDE")
                .enseignant(enseignant)
                .module(module)
                .build();

        documentRepository.save(document);
    }

    public AIPreviewData prepareAiPreview(String titre,
                                          MultipartFile file,
                                          Long moduleId,
                                          String aiType,
                                          Utilisateur enseignant) throws IOException {

        Path tempFolder = Paths.get(tempDir);
        if (!Files.exists(tempFolder)) {
            Files.createDirectories(tempFolder);
        }

        String sourceStoredFileName = System.currentTimeMillis() + "_source_" + file.getOriginalFilename();
        Path sourceStoredPath = tempFolder.resolve(sourceStoredFileName);
        Files.copy(file.getInputStream(), sourceStoredPath, StandardCopyOption.REPLACE_EXISTING);

        String sourceText = pdfTextService.extractText(file);
        String generatedText = aiGenerationService.generate(sourceText, aiType, titre);

        String previewPdfPath = pdfPreviewService.createPreviewPdf(titre, generatedText, aiType);;

        return AIPreviewData.builder()
                .titre(titre)
                .moduleId(moduleId)
                .enseignantId(enseignant.getId())
                .sourceStoredFileName(sourceStoredFileName)
                .sourceStoredPath(sourceStoredPath.toString())
                .sourceOriginalFileName(file.getOriginalFilename())
                .aiType(aiType)
                .generatedText(generatedText)
                .previewPdfPath(previewPdfPath)
                .build();
    }

    public void confirmAiPreview(AIPreviewData previewData, Utilisateur enseignant) throws IOException {
        if (previewData == null) {
            throw new RuntimeException("Aucune prévisualisation IA trouvée.");
        }

        if (!previewData.getEnseignantId().equals(enseignant.getId())) {
            throw new RuntimeException("Accès refusé.");
        }

        Path uploadFolder = Paths.get(uploadDir);
        if (!Files.exists(uploadFolder)) {
            Files.createDirectories(uploadFolder);
        }

        // Le vrai fichier à enregistrer = le PDF généré
        String generatedFileName = System.currentTimeMillis() + "_" + previewData.getAiType() + "_" + previewData.getTitre().replaceAll("[^a-zA-Z0-9-_]", "_") + ".pdf";
        Path finalGeneratedPath = uploadFolder.resolve(generatedFileName);

        Files.move(
                Paths.get(previewData.getPreviewPdfPath()),
                finalGeneratedPath,
                StandardCopyOption.REPLACE_EXISTING
        );

        ModuleCours module = moduleCoursRepository.findById(previewData.getModuleId()).orElseThrow();

        Document document = Document.builder()
                .titre(previewData.getTitre())
                .nomFichier(generatedFileName)
                .cheminFichier(finalGeneratedPath.toString())
                .typeFichier("PDF")
                .dateUpload(LocalDate.now())
                .modeUpload(previewData.getAiType())   // RESUME / QCM / EXAMEN
                .statut("VALIDE")
                .enseignant(enseignant)
                .module(module)
                .build();

        Document savedDocument = documentRepository.save(document);

        ContenuGenere contenuGenere = ContenuGenere.builder()
                .typeContenu(previewData.getAiType())
                .contenu(previewData.getGeneratedText())
                .dateGeneration(LocalDate.now())
                .valide(true)
                .document(savedDocument)
                .build();

        contenuGenereRepository.save(contenuGenere);

        // on supprime le PDF source temporaire, car tu ne veux pas le conserver
        Files.deleteIfExists(Paths.get(previewData.getSourceStoredPath()));
    }

    public void cancelAiPreview(AIPreviewData previewData, Utilisateur enseignant) throws IOException {
        if (previewData == null) {
            return;
        }

        if (previewData.getEnseignantId().equals(enseignant.getId())) {
            Files.deleteIfExists(Paths.get(previewData.getSourceStoredPath()));
            Files.deleteIfExists(Paths.get(previewData.getPreviewPdfPath()));
        }
    }

    public void deleteDocument(Long documentId, Utilisateur enseignant) throws IOException {
        Document document = documentRepository.findById(documentId).orElseThrow();

        if (!document.getEnseignant().getId().equals(enseignant.getId())) {
            throw new RuntimeException("Accès refusé : ce document n'appartient pas à cet enseignant.");
        }

        Path filePath = Paths.get(document.getCheminFichier());
        Files.deleteIfExists(filePath);

        documentRepository.delete(document);
    }
}

