package com.example.Elearning.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmbeddingService {

    private final RestClient restClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.embedding.model}")
    private String embeddingModel;

    public EmbeddingService() {
        this.restClient = RestClient.builder().build();
    }

    public List<Double> embed(String text) {

        Map<String, Object> request = Map.of(
                "model", "models/" + embeddingModel,
                "content", Map.of(
                        "parts", List.of(
                                Map.of("text", text)
                        )
                )
        );

        Map response = restClient.post()
                .uri(apiUrl + "/" + embeddingModel + ":embedContent?key=" + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(Map.class);

        if (response == null || response.get("embedding") == null) {
            throw new RuntimeException("Réponse embedding vide");
        }

        Map embedding = (Map) response.get("embedding");

        return ((List<Number>) embedding.get("values"))
                .stream()
                .map(Number::doubleValue)
                .toList();
    }

    public String toText(List<Double> vector) {
        return vector.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public List<Double> fromText(String text) {
        return Arrays.stream(text.split(","))
                .map(Double::parseDouble)
                .toList();
    }
}
