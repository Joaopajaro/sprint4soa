package com.example.soaauthapp.controller;

import com.example.soaauthapp.dto.JwtResponse;
import com.example.soaauthapp.dto.LoginRequest;
import com.example.soaauthapp.dto.SignupRequest;
import com.example.soaauthapp.model.User;
import com.example.soaauthapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável pelos endpoints de autenticação e registro de
 * usuários. Este controlador utiliza UserService para encapsular a lógica
 * de negócios, mantendo a separação de responsabilidades em camadas.
 * As respostas HTTP retornam códigos apropriados (201 para criação e
 * 200 para autenticação) e os dados de retorno são simples, como seria
 * implementado por um estudante. A validação Bean Validation é aplicada
 * aos DTOs via {@link Valid} para garantir integridade dos dados.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint para registrar um novo usuário. Recebe um JSON com
     * username e password, valida e salva no banco. Retorna o usuário
     * recém-criado ou mensagem de erro caso o usuário já exista.
     *
     * @param request dados de cadastro
     * @return ResponseEntity com status 201 e usuário criado
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest request) {
        try {
            User user = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para autenticar um usuário. Após verificação das
     * credenciais, retorna um token JWT válido para ser utilizado nas
     * requisições subsequentes. Em caso de falha de autenticação,
     * delega a Spring Security para retornar 401.
     *
     * @param request dados de login
     * @return ResponseEntity com token JWT
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {
        JwtResponse response = userService.authenticateUser(request);
        return ResponseEntity.ok(response);
    }
}