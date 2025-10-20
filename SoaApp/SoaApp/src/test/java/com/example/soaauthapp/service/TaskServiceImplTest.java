package com.example.soaauthapp.service;

import com.example.soaauthapp.model.Task;
import com.example.soaauthapp.repository.TaskRepository;
import com.example.soaauthapp.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe TaskServiceImpl. Utiliza Mockito
 * para isolar a dependência TaskRepository e verificar a lógica de
 * negócio sem interagir com o banco de dados. Exemplifica como um
 * estudante pode escrever testes simples e compreensíveis.
 */
@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task exampleTask;

    @BeforeEach
    void setUp() {
        exampleTask = new Task("Tarefa de teste", "Descrição");
        exampleTask.setId(1L);
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(exampleTask));
        List<Task> result = taskService.getAllTasks();
        assertEquals(1, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskByIdExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(exampleTask));
        Task found = taskService.getTaskById(1L);
        assertEquals("Tarefa de teste", found.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskByIdNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskService.getTaskById(99L));
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(exampleTask);
        Task created = taskService.createTask(new Task("Nova", "Desc"));
        assertNotNull(created);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testUpdateTask() {
        Task updatedTask = new Task("Atualizado", "Nova descrição");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(exampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        Task result = taskService.updateTask(1L, updatedTask);
        assertEquals("Atualizado", result.getTitle());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testDeleteTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(exampleTask));
        doNothing().when(taskRepository).delete(exampleTask);
        taskService.deleteTask(1L);
        verify(taskRepository).delete(exampleTask);
    }
}