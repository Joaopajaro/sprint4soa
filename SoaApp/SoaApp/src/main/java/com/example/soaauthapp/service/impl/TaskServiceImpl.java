package com.example.soaauthapp.service.impl;

import com.example.soaauthapp.model.Task;
import com.example.soaauthapp.repository.TaskRepository;
import com.example.soaauthapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementação de TaskService que utiliza TaskRepository para
 * persistência. Encapsula a lógica de negócios e lança exceções
 * apropriadas quando entidades não são encontradas. Esta classe
 * demonstra o uso de polimorfismo ao ser injetada através de sua
 * interface.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Task existing = getTaskById(id);
        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        return taskRepository.save(existing);
    }

    @Override
    public void deleteTask(Long id) {
        Task existing = getTaskById(id);
        taskRepository.delete(existing);
    }
}