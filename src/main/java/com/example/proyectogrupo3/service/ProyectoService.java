package com.example.proyectogrupo3.service;

import com.example.proyectogrupo3.model.Proyecto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ProyectoService {

    public ResponseEntity<Map<String,Object>> listarProyectos();
    public ResponseEntity<Map<String,Object>> listarProyectosPorId(Long id);
    public ResponseEntity<Map<String,Object>> registrarProyecto(Proyecto proyecto, Long id_usuario);
    public ResponseEntity<Map<String,Object>> actualizarProyecto(Proyecto proyecto, Long id_proyecto);
    public ResponseEntity<Map<String,Object>> eliminarProyecto(Long id);
}