package com.example.soaauthapp.service;

import com.example.soaauthapp.dto.JwtResponse;
import com.example.soaauthapp.dto.LoginRequest;
import com.example.soaauthapp.dto.SignupRequest;
import com.example.soaauthapp.model.User;

/**
 * Interface que define operações relacionadas a usuários.
 * Usamos uma interface para permitir extensibilidade e facilitar
 * substituições (princípio da inversão de dependência). O
 * polimorfismo será aplicado quando injetarmos a implementação
 * UserServiceImpl.
 */
public interface UserService {
    User registerUser(SignupRequest request);
    JwtResponse authenticateUser(LoginRequest request);
}