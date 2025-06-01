package com.encuesta.controller;

import com.encuesta.dto.JwtResponse;
import com.encuesta.dto.LoginRequest;
import com.encuesta.dto.RegistroRequest;
import com.encuesta.entity.Usuario;
import com.encuesta.repository.UsuarioRepository;
import com.encuesta.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collections;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("Intentando autenticar usuario: " + loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        System.out.println("Autenticación exitosa para: " + loginRequest.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication.getName());

        // Usa UserDetailsImpl como principal
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername()));
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistroRequest registroRequest) {
        if (usuarioRepository.existsByUsername(registroRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: El nombre de usuario ya está en uso!");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(registroRequest.getUsername());
        usuario.setPassword(passwordEncoder.encode(registroRequest.getPassword()));

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Collections.singletonMap("message", "Usuario registrado correctamente"));
    }
}
