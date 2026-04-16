package com.example.Elearning.Repository;

import com.example.Elearning.Entity.ModuleCours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModuleCoursRepository extends JpaRepository<ModuleCours, Long> {

    @Query("SELECT m FROM ModuleCours m WHERE m.matiere.enseignant.id = :id")
    List<ModuleCours> findByEnseignantId(Long id);

    List<ModuleCours> findByMatiereId(Long matiereId);

}
