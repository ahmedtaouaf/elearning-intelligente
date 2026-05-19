package com.example.Elearning.Service;

import com.example.Elearning.Entity.Document;
import com.example.Elearning.Entity.DocumentChunk;
import com.example.Elearning.Repository.DocumentChunkRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentIndexingService {

    private final DocumentChunkRepository chunkRepository;
    private final EmbeddingService embeddingService;
    private final PdfTextService pdfTextService;

    public DocumentIndexingService(DocumentChunkRepository chunkRepository,
                                   EmbeddingService embeddingService,
                                   PdfTextService pdfTextService) {
        this.chunkRepository = chunkRepository;
        this.embeddingService = embeddingService;
        this.pdfTextService = pdfTextService;
    }

    @Transactional
    public void indexDocument(Document document) {
        try {
            chunkRepository.deleteByDocumentId(document.getId());

            Path path = Paths.get(document.getCheminFichier());
            String text = pdfTextService.extractText(path);

            List<String> chunks = splitText(text, 900);

            for (String chunk : chunks) {
                if (chunk.isBlank()) continue;

                List<Double> vector = embeddingService.embed(chunk);

                DocumentChunk documentChunk = DocumentChunk.builder()
                        .contenu(chunk)
                        .embedding(embeddingService.toText(vector))
                        .document(document)
                        .module(document.getModule())
                        .build();

                chunkRepository.save(documentChunk);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> splitText(String text, int maxLength) {
        List<String> chunks = new ArrayList<>();

        text = text.replace("\r", " ")
                .replace("\n", " ")
                .replaceAll("\\s+", " ")
                .trim();

        String[] words = text.split(" ");
        StringBuilder currentChunk = new StringBuilder();

        for (String word : words) {

            if (currentChunk.length() + word.length() + 1 > maxLength) {
                chunks.add(currentChunk.toString().trim());
                currentChunk = new StringBuilder();
            }

            currentChunk.append(word).append(" ");
        }

        if (!currentChunk.isEmpty()) {
            chunks.add(currentChunk.toString().trim());
        }

        return chunks;
    }
}
