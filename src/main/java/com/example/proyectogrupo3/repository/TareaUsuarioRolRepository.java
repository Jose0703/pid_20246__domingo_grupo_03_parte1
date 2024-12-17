package com.example.proyectogrupo3.repository;

import com.example.proyectogrupo3.model.TareaUsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaUsuarioRolRepository extends JpaRepository<TareaUsuarioRol, Long> {
}
