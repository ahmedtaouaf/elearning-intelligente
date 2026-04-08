package com.example.Elearning.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "contenus_generes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContenuGenere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_contenu", nullable = false, length = 50)
    private String typeContenu;

    @Lob
    @Column(nullable = false)
    private String contenu;

    @Column(name = "date_generation", nullable = false)
    private LocalDate dateGeneration;

    @Column(nullable = false)
    private Boolean valide = false;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;
}
