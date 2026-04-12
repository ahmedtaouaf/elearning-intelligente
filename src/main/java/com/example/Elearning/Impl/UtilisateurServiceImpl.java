package com.example.Elearning.Impl;

import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.UtilisateurRepository;
import com.example.Elearning.Service.UtilisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,
                                  PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur getById(Long id) {
        return utilisateurRepository.findById(id).orElseThrow();
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {

        if (utilisateur.getId() != null) {
            Utilisateur oldUser = utilisateurRepository.findById(utilisateur.getId()).orElseThrow();

            if (utilisateur.getMotDePasse() == null || utilisateur.getMotDePasse().isBlank()) {
                utilisateur.setMotDePasse(oldUser.getMotDePasse());
            } else {
                utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
            }
        } else {
            utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        }

        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void delete(Long id) {
        utilisateurRepository.deleteById(id);
    }
}
