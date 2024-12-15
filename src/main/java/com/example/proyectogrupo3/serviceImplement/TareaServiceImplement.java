package com.example.proyectogrupo3.serviceImplement;


import com.example.proyectogrupo3.model.Tarea;
import com.example.proyectogrupo3.repository.TareaRepository;
import com.example.proyectogrupo3.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TareaServiceImplement  implements TareaService {
                 @Autowired
         private TareaRepository tareaRepository;
    @Override
    public ResponseEntity<Map<String, Object>> ObtenerTareasporProyecto(Long idProyecto) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> registrarTarea(Tarea tarea) {
        return null;
    }



    @Override
    public ResponseEntity<Map<String, Object>> actualizarTarea(Tarea tarea, Long id_tarea) {
        Map<String, Object> response = new HashMap<>();

        // Buscar la tarea existente por su ID
        Optional<Tarea> tareaExistente = tareaRepository.findById(id_tarea);

        if (tareaExistente.isPresent()) {
            Tarea tareaActualizada = tareaExistente.get();

            // Verificar si ya existe una tarea con el mismo nombre en el mismo proyecto
            if (tareaRepository.existsByNombre(tarea.getNombreProyecto())){
                response.put("mensaje", "Ya existe una tarea con este nombre en el proyecto");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            // Actualizar los campos de la tarea
            tareaActualizada.setNombre(tarea.getNombre());
            tareaActualizada.setDescripcion(tarea.getDescripcion());
            tareaActualizada.setFechavencimineto(tarea.getFechavencimineto());

            // Guardar la tarea actualizada
            tareaRepository.save(tareaActualizada);

            response.put("mensaje", "Tarea actualizada correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("mensaje", "La tarea no existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @Override
    public ResponseEntity<Map<String, Object>> eliminarTareas(Long id) {
        return null;
    }

}
