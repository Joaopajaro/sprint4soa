package com.example.soaauthapp.security;

import com.example.soaauthapp.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Classe utilitária responsável pela geração e validação de tokens JWT.
 * Usa a biblioteca jjwt para manipular os tokens. Em um projeto real,
 * as propriedades secret e expiration poderiam vir de
 * application.properties, mas aqui fornecemos valores padrão para fins
 * didáticos.
 */
@Component
public class JwtUtils {
    // Segredo utilizado para assinar o token. Em produção utilize uma
    // string mais longa e armazene em local seguro.
    @Value("${soaauthapp.jwtSecret:MyJwtSecretKey1234567890}")
    private String jwtSecret;

    // Tempo de expiração em milissegundos (ex.: 24 horas)
    @Value("${soaauthapp.jwtExpirationMs:86400000}")
    private int jwtExpirationMs;

    private Key getSigningKey() {
        // Converte a chave secreta em uma Key para assinatura HS512
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}