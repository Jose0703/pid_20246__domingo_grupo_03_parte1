package com.example.proyectogrupo3.repository;

import com.example.proyectogrupo3.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    boolean existsByNombre(String nombre);
}
