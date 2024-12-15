package com.example.proyectogrupo3.service;



import com.example.proyectogrupo3.model.Tarea;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TareaService {

     ResponseEntity<Map<String,Object>> ObtenerTareasporProyecto( Long idProyecto);
     ResponseEntity<Map<String,Object>> registrarTarea(Tarea tarea);

     public ResponseEntity<Map<String,Object>> actualizarTarea(Tarea tarea, Long id_tarea);
     public ResponseEntity<Map<String,Object>> eliminarTareas(Long id);
}
