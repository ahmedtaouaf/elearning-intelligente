package com.example.Elearning.Repository;

import com.example.Elearning.Entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    @Query("SELECT m FROM Matiere m WHERE m.enseignant.id = :id")
    List<Matiere> findByEnseignantId(Long id);
}
