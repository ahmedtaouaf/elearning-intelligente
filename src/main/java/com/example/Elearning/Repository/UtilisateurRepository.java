package com.example.Elearning.Repository;

import com.example.Elearning.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);

    long countByRoleNom(String nom);

    long countByActifTrue();
}
