package com.example.proyectogrupo3.repository;


import com.example.proyectogrupo3.model.Tarea;
import com.example.proyectogrupo3.model.TareaUsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaUsuarioRolRepository extends JpaRepository<TareaUsuarioRol, Long> {

    List<TareaUsuarioRol> findByTarea(Tarea tarea);
}
