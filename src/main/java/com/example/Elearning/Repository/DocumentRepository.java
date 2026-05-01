package com.example.Elearning.Repository;

import com.example.Elearning.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByModuleId(Long moduleId);

    long countByModeUpload(String modeUpload);

    long countByEnseignantId(Long enseignantId);

    long countByEnseignantIdAndModeUpload(Long enseignantId, String modeUpload);

    long countByEnseignantIdAndTypeFichier(Long enseignantId, String typeFichier);

    List<Document> findTop5ByOrderByDateUploadDesc();

    List<Document> findTop5ByEnseignantIdOrderByDateUploadDesc(Long enseignantId);

    @Query("""
        SELECT d FROM Document d
        WHERE d.module.niveau.id = :niveauId
        AND d.module.filiere.id = :filiereId
        ORDER BY d.dateUpload DESC
    """)
    List<Document> findDocumentsForStudent(Long niveauId, Long filiereId);

    @Query("""
        SELECT COUNT(d) FROM Document d
        WHERE d.module.niveau.id = :niveauId
        AND d.module.filiere.id = :filiereId
    """)
    long countDocumentsForStudent(Long niveauId, Long filiereId);

    @Query("""
        SELECT COUNT(d) FROM Document d
        WHERE d.module.niveau.id = :niveauId
        AND d.module.filiere.id = :filiereId
        AND d.modeUpload = :modeUpload
    """)
    long countDocumentsForStudentByMode(Long niveauId, Long filiereId, String modeUpload);

    long countByTypeFichier(String typeFichier);

    @Query("""
SELECT COUNT(d) FROM Document d
WHERE d.enseignant.id = :enseignantId
AND d.modeUpload = :modeUpload
""")
    long countTeacherDocumentsByMode(Long enseignantId, String modeUpload);

    @Query("""
SELECT COUNT(d) FROM Document d
WHERE d.enseignant.id = :enseignantId
AND d.typeFichier = :typeFichier
""")
    long countTeacherDocumentsByType(Long enseignantId, String typeFichier);

    @Query("""
SELECT COUNT(d) FROM Document d
WHERE d.module.niveau.id = :niveauId
AND d.module.filiere.id = :filiereId
AND d.typeFichier = :typeFichier
""")
    long countDocumentsForStudentByType(Long niveauId, Long filiereId, String typeFichier);

}
