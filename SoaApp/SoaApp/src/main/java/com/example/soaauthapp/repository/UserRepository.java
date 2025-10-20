package com.example.soaauthapp.repository;

import com.example.soaauthapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório de dados para a entidade User. Estende JpaRepository para
 * fornecer métodos CRUD e define uma consulta adicional para buscar
 * usuário pelo nome. Usamos Optional para lidar com ausência de dados.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}