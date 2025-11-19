package com.domo.domo.security.controller;

import com.domo.domo.security.services.AppUserDetailsService;
import com.domo.domo.security.tokenisation.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final AppUserDetailsService appUserDetailsService;

    // Injection via le constructeur
    @Autowired
    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, AppUserDetailsService appUserDetailsService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.appUserDetailsService = appUserDetailsService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest request) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails user = appUserDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(user);

        return Map.of("token", token);
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody AuthRequest request) {
        if (appUserDetailsService.existsByUsername(request.getUsername())) {
            //throw new RuntimeException("Username déjà utilisé");
            return Map.of("message", "Username déjà utilisé");
        }
        appUserDetailsService.saveUser(request.getUsername(), request.getPassword(), request.getRole());
        return Map.of("message", "Utilisateur créé avec succès");
    }


}
