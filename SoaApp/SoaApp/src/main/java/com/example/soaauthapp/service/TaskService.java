package com.example.soaauthapp.service;

import com.example.soaauthapp.model.Task;

import java.util.List;

/**
 * Interface de serviço para operações de tarefas. Encapsula a lógica de
 * negócios, separando a camada de controle (controller) da persistência
 * (repository). Esse isolamento facilita testes unitários e segue
 * princípios SOLID.
 */
public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
}