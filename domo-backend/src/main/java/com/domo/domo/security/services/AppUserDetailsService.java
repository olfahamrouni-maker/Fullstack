package com.domo.domo.security.services;


import com.domo.domo.security.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserService appUserService;

    // Injection via le constructeur
    @Autowired
    public AppUserDetailsService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser appUser = appUserService.loadByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(appUser.getRole().name())
                .build();
    }

    // Vérifie si le username existe déjà
    public boolean existsByUsername(String username) {
        return appUserService.existsByUsername(username);
    }

    // Sauvegarde un nouvel utilisateur
    public AppUser saveUser(String username, String password, String role) {
        return appUserService.saveUser(username, password, role);
    }
}
