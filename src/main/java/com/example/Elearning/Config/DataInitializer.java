package com.example.Elearning.Config;


import com.example.Elearning.Entity.Utilisateur;
import com.example.Elearning.Repository.RoleRepository;
import com.example.Elearning.Repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.Elearning.Entity.Role;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepository,
                               UtilisateurRepository utilisateurRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {

            Role adminRole = roleRepository.findByNom("ADMIN")
                    .orElseGet(() -> roleRepository.save(Role.builder().nom("ADMIN").build()));

            Role enseignantRole = roleRepository.findByNom("ENSEIGNANT")
                    .orElseGet(() -> roleRepository.save(Role.builder().nom("ENSEIGNANT").build()));

            Role etudiantRole = roleRepository.findByNom("ETUDIANT")
                    .orElseGet(() -> roleRepository.save(Role.builder().nom("ETUDIANT").build()));

            if (utilisateurRepository.findByEmail("admin@gmail.com").isEmpty()) {
                utilisateurRepository.save(
                        Utilisateur.builder()
                                .nom("Admin")
                                .prenom("System")
                                .email("admin@gmail.com")
                                .motDePasse(passwordEncoder.encode("admin123"))
                                .actif(true)
                                .role(adminRole)
                                .build()
                );
            }

            if (utilisateurRepository.findByEmail("enseignant@gmail.com").isEmpty()) {
                utilisateurRepository.save(
                        Utilisateur.builder()
                                .nom("Ahmed")
                                .prenom("Prof")
                                .email("enseignant@gmail.com")
                                .motDePasse(passwordEncoder.encode("prof123"))
                                .actif(true)
                                .role(enseignantRole)
                                .build()
                );
            }

            if (utilisateurRepository.findByEmail("etudiant@gmail.com").isEmpty()) {
                utilisateurRepository.save(
                        Utilisateur.builder()
                                .nom("Sara")
                                .prenom("Etudiante")
                                .email("etudiant@gmail.com")
                                .motDePasse(passwordEncoder.encode("etu123"))
                                .actif(true)
                                .role(etudiantRole)
                                .build()
                );
            }
        };
    }
}
