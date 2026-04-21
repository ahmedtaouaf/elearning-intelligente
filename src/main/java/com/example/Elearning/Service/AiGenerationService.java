package com.example.Elearning.Service;

import com.example.Elearning.Dto.Content;
import com.example.Elearning.Dto.GeminiRequest;
import com.example.Elearning.Dto.GeminiResponse;
import com.example.Elearning.Dto.Part;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AiGenerationService {

    private final RestClient restClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public AiGenerationService() {
        this.restClient = RestClient.builder().build();
    }

    public String generate(String sourceText, String aiType, String titre) {
        String prompt = buildPrompt(sourceText, aiType, titre);

        GeminiRequest request = new GeminiRequest(
                List.of(
                        new Content(
                                List.of(new Part(prompt))
                        )
                )
        );

        GeminiResponse response = restClient.post()
                .uri(apiUrl + "/" + model + ":generateContent?key=" + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(GeminiResponse.class);

        if (response == null
                || response.getCandidates() == null
                || response.getCandidates().isEmpty()
                || response.getCandidates().get(0).getContent() == null
                || response.getCandidates().get(0).getContent().getParts() == null
                || response.getCandidates().get(0).getContent().getParts().isEmpty()) {
            throw new RuntimeException("Réponse Gemini vide ou invalide.");
        }

        return response.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

    private String buildPrompt(String sourceText, String aiType, String titre) {
        String cleaned = sourceText == null ? "" : sourceText.trim();
        if (cleaned.length() > 12000) {
            cleaned = cleaned.substring(0, 12000);
        }

        return switch (aiType.toUpperCase()) {
                                case "RESUME" -> """
                            Tu es un assistant pédagogique.
                            Génère un résumé clair, structuré et fidèle du document suivant.
                    
                            Contraintes importantes :
                            - Réponds directement sans introduction
                            - N’écris pas "Voici le résumé", "Absolument", "Bien sûr"
                            - Commence directement par le titre
                            - Utiliser des sections claires
                            - Ne pas inventer d'informations
                            - Style académique simple
                    
                            Format attendu :
                            TITRE: Résumé de ...
                            SECTION: Introduction
                            ...
                            SECTION: Idées principales
                            ...
                            SECTION: Conclusion
                            ...
                            
                            Titre du document : %s
                    
                            Contenu source :
                            %s
                            """.formatted(titre, cleaned);

                                case "QCM" -> """
                            Tu es un assistant pédagogique.
                            Génère un QCM en français à partir du document suivant.
                    
                            Contraintes importantes :
                            - Réponds directement sans introduction
                            - N’écris pas de phrases comme "Voici un QCM", "Absolument", "Bien sûr"
                            - Ne mets aucun commentaire avant le contenu
                            - Commence directement par le titre du QCM
                            - 10 questions
                            - 4 choix par question
                            - Indiquer la bonne réponse après chaque question
                            - Niveau universitaire
                            - Ne pas inventer des notions absentes du document
                            Format attendu :
                            TITRE: QCM sur ...
                            QUESTION 1:
                            Texte de la question
                            A) ...
                            B) ...
                            C) ...
                            D) ...
                            REPONSE: ...
                            
                            Titre du document : %s
                    
                            Contenu source :
                            %s
                            """.formatted(titre, cleaned);

                                case "EXAMEN" -> """
                            Tu es un assistant pédagogique.
                            Génère un sujet d'examen en français à partir du document suivant.
                    
                            Contraintes importantes :
                            - Réponds directement sans introduction
                            - N’écris pas "Voici un examen", "Absolument", "Bien sûr"
                            - Commence directement par le titre
                            - Structure claire
                            - Niveau universitaire
                            - Ne pas inventer d'informations hors document
                    
                            Format attendu :
                            TITRE: Examen sur ...
                            SECTION: Consignes
                            ...
                            SECTION: Partie 1
                            ...
                            SECTION: Partie 2
                            ...
                    
                            Titre du document : %s
                    
                            Contenu source :
                            %s
                            """.formatted(titre, cleaned);

            default -> throw new RuntimeException("Type IA non reconnu : " + aiType);
        };
    }
}
