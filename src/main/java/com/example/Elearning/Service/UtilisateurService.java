package com.example.Elearning.Service;


import com.example.Elearning.Entity.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    List<Utilisateur> getAll();

    Utilisateur getById(Long id);

    Utilisateur save(Utilisateur utilisateur);

    void delete(Long id);
}
