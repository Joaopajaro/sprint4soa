package com.example.soaauthapp.service.impl;

import com.example.soaauthapp.dto.JwtResponse;
import com.example.soaauthapp.dto.LoginRequest;
import com.example.soaauthapp.dto.SignupRequest;
import com.example.soaauthapp.model.User;
import com.example.soaauthapp.model.UserRole;
import com.example.soaauthapp.repository.UserRepository;
import com.example.soaauthapp.security.JwtUtils;
import com.example.soaauthapp.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implementação concreta de UserService que contém a lógica de
 * autenticação e registro de usuários. Utiliza PasswordEncoder para
 * criptografar senhas e JwtUtils para gerar tokens. A anotação
 * @Service permite que o Spring injete esta classe onde a
 * interface UserService é esperada.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    @Transactional
    public User registerUser(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username já existente");
        }
        // Criptografa a senha antes de salvar
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getUsername(), encodedPassword, Collections.singleton(UserRole.ROLE_USER));
        return userRepository.save(user);
    }

    @Override
    public JwtResponse authenticateUser(LoginRequest request) {
        // Autentica as credenciais usando AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        String jwt = jwtUtils.generateJwtToken(user);
        return new JwtResponse(jwt);
    }
}