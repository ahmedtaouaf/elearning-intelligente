package com.example.Elearning.Repository;

import com.example.Elearning.Entity.Matiere;
import com.example.Elearning.Entity.ModuleCours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ModuleCoursRepository extends JpaRepository<ModuleCours, Long> {

    @Query("SELECT m FROM ModuleCours m WHERE m.matiere.enseignant.id = :id")
    List<ModuleCours> findByEnseignantId(Long id);

    List<ModuleCours> findByMatiereId(Long matiereId);

    @Query("""
        SELECT DISTINCT m.matiere FROM ModuleCours m
        WHERE m.niveau.id = :niveauId
        AND m.filiere.id = :filiereId
        """)
    List<Matiere> findMatieresForStudent(Long niveauId, Long filiereId);

    @Query("""
SELECT m FROM ModuleCours m
WHERE m.matiere.id = :matiereId
AND m.niveau.id = :niveauId
AND m.filiere.id = :filiereId
""")
    List<ModuleCours> findModulesForStudentByMatiere(Long matiereId, Long niveauId, Long filiereId);

    @Query("""
SELECT m FROM ModuleCours m
WHERE m.id = :moduleId
AND m.niveau.id = :niveauId
AND m.filiere.id = :filiereId
""")
    Optional<ModuleCours> findModuleForStudent(Long moduleId, Long niveauId, Long filiereId);

}
