package com.example.soaauthapp.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para autenticação. Contém nome de usuário e senha. Será usado
 * para criar um UsernamePasswordAuthenticationToken no serviço.
 */
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}