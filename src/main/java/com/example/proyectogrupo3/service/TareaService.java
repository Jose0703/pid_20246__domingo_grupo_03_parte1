package com.example.proyectogrupo3.service;

import com.example.proyectogrupo3.model.Tarea;
import com.example.proyectogrupo3.model.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TareaService {
    public ResponseEntity<Map<String,Object>> listarTarea();
    public ResponseEntity<Map<String,Object>> listarTareaPorId(Long id);
    public ResponseEntity<Map<String,Object>> registrarTarea(Tarea tarea);
    public ResponseEntity<Map<String,Object>> actualizarTarea(Tarea tarea, Long id_proyecto);
    public ResponseEntity<Map<String,Object>> eliminarTarea(Long id_proyecto);
    Tarea agregarComentario(Long idTarea, String comentario);

    void asignarRolATarea(Long idTarea, Long IdUsuario, String rol);


    List<Tarea> obtenerTareasConUsuariosYRoles();
    Tarea guardarTarea(Tarea tarea);
}
