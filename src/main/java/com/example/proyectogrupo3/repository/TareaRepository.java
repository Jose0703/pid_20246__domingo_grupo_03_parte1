package com.example.proyectogrupo3.repository;

import com.example.proyectogrupo3.model.Proyecto;
import com.example.proyectogrupo3.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository  extends JpaRepository<Tarea, Long> {


       List<Tarea> findByNombreAndProyecto(String nombre , Proyecto proyecto);

           boolean existsByNombre(String nombre);



}
