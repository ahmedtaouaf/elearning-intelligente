package com.example.Elearning.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurDto {

    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    private Long roleId;
}
