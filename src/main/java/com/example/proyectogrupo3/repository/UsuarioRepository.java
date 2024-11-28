package com.example.proyectogrupo3.repository;

import com.example.proyectogrupo3.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findOneByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNombre(String nombre);
    boolean existsByDni(String dni);
}
