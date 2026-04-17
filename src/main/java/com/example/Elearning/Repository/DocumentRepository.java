package com.example.Elearning.Repository;

import com.example.Elearning.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
