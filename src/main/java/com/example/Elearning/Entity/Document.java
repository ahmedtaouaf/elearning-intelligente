package com.example.Elearning.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titre;

    @Column(name = "nom_fichier", nullable = false, length = 255)
    private String nomFichier;

    @Column(name = "chemin_fichier", nullable = false, length = 255)
    private String cheminFichier;

    @Column(name = "type_fichier", nullable = false, length = 50)
    private String typeFichier;

    @Column(name = "date_upload", nullable = false)
    private LocalDate dateUpload;

    @Column(name = "mode_upload", nullable = false, length = 50)
    private String modeUpload;

    @Column(nullable = false, length = 50)
    private String statut;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Utilisateur enseignant;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private ModuleCours module;

    @OneToMany(mappedBy = "document")
    @Builder.Default
    private List<ContenuGenere> contenusGeneres = new ArrayList<>();
}
