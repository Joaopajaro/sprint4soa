package com.example.soaauthapp.dto;

/**
 * DTO retornado após autenticação bem-sucedida. Contém apenas o
 * token JWT gerado pelo serviço, mas em aplicações reais poderia
 * incluir também informações sobre o usuário e roles.
 */
public class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}