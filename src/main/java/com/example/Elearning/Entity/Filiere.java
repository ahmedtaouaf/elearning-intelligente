package com.example.Elearning.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "filieres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nom;
}
