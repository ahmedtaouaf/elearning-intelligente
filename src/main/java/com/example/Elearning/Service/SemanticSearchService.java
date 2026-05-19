package com.example.Elearning.Service;

import com.example.Elearning.Dto.SemanticSearchResult;
import com.example.Elearning.Entity.DocumentChunk;
import com.example.Elearning.Repository.DocumentChunkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SemanticSearchService {

    private final DocumentChunkRepository chunkRepository;
    private final EmbeddingService embeddingService;
    private final AiGenerationService aiGenerationService;


    public SemanticSearchService(DocumentChunkRepository chunkRepository,
                                 EmbeddingService embeddingService, AiGenerationService aiGenerationService) {
        this.chunkRepository = chunkRepository;
        this.embeddingService = embeddingService;
        this.aiGenerationService = aiGenerationService;
    }

    public SemanticSearchResult searchInModule(Long moduleId,
                                               String question) {

        List<DocumentChunk> chunks =
                chunkRepository.findByModuleId(moduleId);

        if (chunks.isEmpty()) {
            return new SemanticSearchResult(
                    "Aucun contenu indexé pour ce module.",
                    List.of()
            );
        }

        List<Double> questionEmbedding =
                embeddingService.embed(question);

        List<ScoredChunk> scoredChunks = new ArrayList<>();

        for (DocumentChunk chunk : chunks) {

            List<Double> chunkEmbedding =
                    embeddingService.fromText(chunk.getEmbedding());

            double score =
                    cosineSimilarity(questionEmbedding, chunkEmbedding);

            scoredChunks.add(new ScoredChunk(
                    chunk.getContenu(),
                    score
            ));
        }

        List<String> bestPassages = scoredChunks.stream()
                .sorted((a, b) -> Double.compare(b.score(), a.score()))
                .limit(2)
                .map(ScoredChunk::contenu)
                .toList();

        String context = String.join("\n\n---\n\n", bestPassages);

        String response = aiGenerationService.answerFromContext(question, context);

        return new SemanticSearchResult(
                response,
                bestPassages
        );
    }

    private double cosineSimilarity(List<Double> v1,
                                    List<Double> v2) {

        double dot = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < v1.size(); i++) {
            dot += v1.get(i) * v2.get(i);
            norm1 += Math.pow(v1.get(i), 2);
            norm2 += Math.pow(v2.get(i), 2);
        }

        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    record ScoredChunk(String contenu, double score) {}
}
