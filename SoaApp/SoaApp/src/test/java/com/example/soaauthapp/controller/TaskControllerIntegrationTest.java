package com.example.soaauthapp.controller;

import com.example.soaauthapp.dto.LoginRequest;
import com.example.soaauthapp.dto.SignupRequest;
import com.example.soaauthapp.model.Task;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste de integração para os endpoints de tarefas com autenticação JWT.
 * Este teste inicia o contexto Spring completo, registra um usuário,
 * efetua login e utiliza o token retornado para acessar os endpoints
 * de criação e listagem de tarefas. Demonstra como um aluno pode
 * escrever testes de integração que cobrem múltiplas camadas da
 * aplicação.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndListTasksWithJwt() throws Exception {
        // Registra usuário
        SignupRequest signup = new SignupRequest();
        signup.setUsername("integracao");
        signup.setPassword("senha123");
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signup)))
                .andExpect(status().isCreated());

        // Login e captura token
        LoginRequest login = new LoginRequest();
        login.setUsername("integracao");
        login.setPassword("senha123");
        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode loginJson = objectMapper.readTree(loginResult.getResponse().getContentAsString());
        String token = loginJson.get("token").asText();

        // Cria uma nova tarefa
        Task newTask = new Task("Estudar SOA", "Ler material de aula");
        MvcResult createResult = mockMvc.perform(post("/api/tasks")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isCreated())
                .andReturn();
        // Verifica que o corpo da resposta possui o título correto
        JsonNode createdJson = objectMapper.readTree(createResult.getResponse().getContentAsString());
        String createdTitle = createdJson.get("title").asText();
        assert(createdTitle.equals("Estudar SOA"));

        // Lista tarefas e garante que a nova tarefa está presente
        mockMvc.perform(get("/api/tasks")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Estudar SOA"));
    }
}