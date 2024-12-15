package com.example.proyectogrupo3.serviceImplement;

import com.example.proyectogrupo3.model.Tarea;
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

    @Override
    public ResponseEntity<Map<String, Object>> registrarTarea(Tarea tarea) {
        Map<String, Object> respuesta = new HashMap<>();

        if(dao.existsByNombre(tarea.getNombre())){
            respuesta.put("mensaje", "Tarea existente con el mismo nombre");
            respuesta.put("status", HttpStatus.CONFLICT);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
        }else{
            dao.save(tarea);
            respuesta.put("Tarea", tarea);
            respuesta.put("mensaje", "Se añadio correctamente la tarea");
            respuesta.put("status", HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
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
