package com.example.Elearning.Service;

import com.example.Elearning.Entity.Matiere;

import java.util.List;

public interface MatiereService {
    List<Matiere> getAll();
    Matiere getById(Long id);
    Matiere save(Matiere matiere);
    void delete(Long id);
}
