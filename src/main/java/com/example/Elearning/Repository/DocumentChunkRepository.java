package com.example.Elearning.Repository;

import com.example.Elearning.Entity.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long> {

    List<DocumentChunk> findByModuleId(Long moduleId);

    void deleteByDocumentId(Long documentId);
}
