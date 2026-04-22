package com.example.Elearning.Dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIPreviewData {
    private String titre;
    private Long moduleId;
    private Long enseignantId;

    private String sourceStoredFileName;
    private String sourceStoredPath;
    private String sourceOriginalFileName;

    private String aiType;
    private String generatedText;
    private String previewPdfPath;

    @Builder.Default
    private List<ChatMessageDto> messages = new ArrayList<>();
}
