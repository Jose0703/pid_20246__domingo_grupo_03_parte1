package com.example.proyectogrupo3.service;

import com.example.proyectogrupo3.model.Proyecto;
import com.example.proyectogrupo3.model.Tarea;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TareaService {
    public ResponseEntity<Map<String,Object>> listarTarea();
    public ResponseEntity<Map<String,Object>> listarTareaPorId(Long id);
    public ResponseEntity<Map<String,Object>> registrarTarea(Tarea tarea);
    public ResponseEntity<Map<String,Object>> actualizarTarea(Tarea tarea, Long id_proyecto);
    public ResponseEntity<Map<String,Object>> eliminarTarea(Long id_proyecto);
    Tarea agregarComentario(Long idTarea, String comentario);

}
