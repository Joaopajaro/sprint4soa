package com.example.soaauthapp.controller;

import com.example.soaauthapp.model.Task;
import com.example.soaauthapp.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por expor os endpoints de CRUD de tarefas.
 * As operações são restritas a usuários autenticados (via
 * {@link PreAuthorize} ou configuração global). A lógica de negócios
 * permanece encapsulada em {@link TaskService}, demonstrando a
 * separação de responsabilidades típica de uma arquitetura orientada a
 * serviços. Os retornos seguem um padrão simples para facilitar o
 * entendimento e foram escritos de forma humanizada.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Lista todas as tarefas cadastradas.
     *
     * @return lista de tarefas
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Busca uma tarefa específica por id.
     *
     * @param id identificador
     * @return tarefa encontrada ou 404 se não existir
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        try {
            Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Cria uma nova tarefa.
     *
     * @param task dados da tarefa
     * @return tarefa criada
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualiza uma tarefa existente.
     *
     * @param id   id da tarefa a ser atualizada
     * @param task dados de atualização
     * @return tarefa atualizada ou 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        try {
            Task updated = taskService.updateTask(id, task);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Remove uma tarefa pelo id.
     *
     * @param id identificador
     * @return sem conteúdo caso remova, 404 caso não exista
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}