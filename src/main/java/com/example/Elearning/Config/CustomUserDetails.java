package com.example.Elearning.Config;

import com.example.Elearning.Entity.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final Utilisateur utilisateur;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Utilisateur utilisateur,
                             Collection<? extends GrantedAuthority> authorities) {
        this.utilisateur = utilisateur;
        this.authorities = authorities;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return utilisateur.getMotDePasse();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return utilisateur.getActif();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
}
