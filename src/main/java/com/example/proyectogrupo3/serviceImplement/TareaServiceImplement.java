package com.example.proyectogrupo3.serviceImplement;

import com.example.proyectogrupo3.model.Proyecto;
import com.example.proyectogrupo3.model.Tarea;
import com.example.proyectogrupo3.repository.ProyectoRepository;
import com.example.proyectogrupo3.repository.TareaRepository;
import com.example.proyectogrupo3.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TareaServiceImplement implements TareaService {

    @Autowired
    private TareaRepository dao;
    @Autowired
    private ProyectoRepository proyectoRepository;

    @Override
    public ResponseEntity<Map<String, Object>> listarTarea() {
        Map<String, Object> respuesta = new HashMap<>();
        List<Tarea> tareas = dao.findAll();

        if (!tareas.isEmpty()){
            respuesta.put("mensaje", "Lista de tareas");
            respuesta.put("tareas", tareas);
            respuesta.put("status", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.put("mensaje", "No existen registros");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> listarTareaPorId(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Tarea> tareas = dao.findById(id);

        if (!tareas.isEmpty()){
            respuesta.put("tareas", tareas);
            respuesta.put("mensaje", "busqueda correcta");
            respuesta.put("status", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.put("mensaje", "No existen registros con el ID: " + id);
            respuesta.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    public ResponseEntity<Map<String, Object>> registrarTarea(Tarea tarea) {
        Map<String, Object> respuesta = new HashMap<>();

        // Verificar si el Proyecto existe
        Proyecto proyecto = tarea.getProyecto();

        try {
            // Verificar si el Proyecto existe en la base de datos antes de asociarlo
            if (proyecto.getIdProyecto() != null) {
                Proyecto finalProyecto = proyecto;
                proyecto = proyectoRepository.findById(proyecto.getIdProyecto())
                        .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + finalProyecto.getIdProyecto()));
            } else {
                // Si no tiene id, significa que es nuevo, se puede guardar el proyecto (si necesario)
                proyectoRepository.save(proyecto);
            }

            // Asignar el Proyecto a la Tarea
            tarea.setProyecto(proyecto);

            // Verificar si la tarea ya existe
            if (dao.existsByNombre(tarea.getNombre())) {
                respuesta.put("mensaje", "Tarea existente con el mismo nombre");
                respuesta.put("status", HttpStatus.CONFLICT);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                try {
                    dao.save(tarea);  // Guardar la tarea
                    respuesta.put("Tarea", tarea);
                    respuesta.put("mensaje", "Se añadió correctamente la tarea");
                    respuesta.put("status", HttpStatus.CREATED);
                    return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
                } catch (Exception e) {
                    respuesta.put("mensaje", "Error interno del servidor: " + e.getMessage());
                    respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
                }
            }
        } catch (RuntimeException e) {
            respuesta.put("mensaje", "Error al encontrar el proyecto: " + e.getMessage());
            respuesta.put("status", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }



    @Override
    public ResponseEntity<Map<String, Object>> actualizarTarea(Tarea tarea, Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Tarea> tareaExiste = dao.findById(id);
        if(tareaExiste.isPresent()){
            Tarea tareaGet = tareaExiste.get();

            if(dao.existsByNombre(tarea.getNombre())){
                respuesta.put("mensaje", "Ya existe una tarea con este nombre");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            }

            tareaGet.setNombre(tarea.getNombre());
            tareaGet.setDescripcion(tarea.getDescripcion());
            tareaGet.setFechaVencimiento(tarea.getFechaVencimiento());
            tareaGet.setDesarrollado(tarea.getDesarrollado());
            tareaGet.setPrioridad(tarea.getPrioridad());
            tareaGet.setProyecto(tarea.getProyecto());
            dao.save(tarea);
            respuesta.put("tarea", tareaGet);
            respuesta.put("mensaje", "Datos de la tarea modificada");
            respuesta.put("status", HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }else{
            respuesta.put("mensaje", "sin registros con ID: "+ id);
            respuesta.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> eliminarTarea(Long id) {
        Map<String,Object> respuesta = new HashMap<>();
        Optional<Tarea> tareaExiste = dao.findById(id);
        if (tareaExiste.isPresent()) {
            Tarea tarea = tareaExiste.get();
            dao.delete(tarea);
            respuesta.put("mensaje", "Eliminado correctamente");
            respuesta.put("status", HttpStatus.NO_CONTENT);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
        }else {
            respuesta.put("mensaje", "Sin registros con ID: " + id);
            respuesta.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}

