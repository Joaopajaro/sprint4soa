package com.example.soaauthapp.model;

/**
 * Enumeração que representa os perfis (roles) de um usuário. Uma
 * aplicação real poderia separar roles em uma tabela, mas para fins
 * didáticos utilizamos um enum simples. Cada role será armazenado como
 * uma string no banco de dados.
 */
public enum UserRole {
    ROLE_USER,
    ROLE_ADMIN
}