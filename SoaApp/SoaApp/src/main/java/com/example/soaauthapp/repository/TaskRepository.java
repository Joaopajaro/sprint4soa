package com.example.soaauthapp.repository;

import com.example.soaauthapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de tarefas. Estende JpaRepository para operações CRUD
 * automáticas. Este repositório será injetado no serviço TaskService.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}