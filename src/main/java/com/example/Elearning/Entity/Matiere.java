package com.example.Elearning.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matieres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Utilisateur enseignant;

    @OneToMany(mappedBy = "matiere")
    @Builder.Default
    private List<ModuleCours> modules = new ArrayList<>();
}
