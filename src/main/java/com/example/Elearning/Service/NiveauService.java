package com.example.Elearning.Service;

import com.example.Elearning.Entity.Niveau;

import java.util.List;

public interface NiveauService {
    List<Niveau> getAll();
    Niveau getById(Long id);
    Niveau save(Niveau niveau);
    void delete(Long id);
}
