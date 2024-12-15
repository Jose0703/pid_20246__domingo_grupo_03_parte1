package com.example.proyectogrupo3.repository;

import com.example.proyectogrupo3.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    boolean existsByNombre(String nombre);
}
