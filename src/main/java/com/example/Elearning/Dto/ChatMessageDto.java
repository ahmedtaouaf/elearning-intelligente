package com.example.Elearning.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {
    private String sender;   // "USER" ou "AI"
    private String message;
    private String time;
}
