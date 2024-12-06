package com.example.proyectogrupo3.serviceImplement;

import com.example.proyectogrupo3.model.Proyecto;
import com.example.proyectogrupo3.repository.ProyectoRepository;
import com.example.proyectogrupo3.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProyectoServiceImplement implements ProyectoService {
    @Autowired
    private ProyectoRepository proyectoRepository;

    @Override
    public ResponseEntity<Map<String, Object>> listarProyectos() {
        Map<String, Object> response = new HashMap<>();
        List<Proyecto> proyectos = proyectoRepository.findAll();

        if (proyectos.isEmpty()) {
            response.put("mensaje", "No se encontraron proyectos");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            response.put("mensaje", "Proyectos encontrados");
            response.put("proyectos", proyectos);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> registrarProyecto(Proyecto proyecto) {
        Map<String, Object> response = new HashMap<>();
        if (proyectoRepository.existsByNombre(proyecto.getNombre())) {
            response.put("mensaje", "El proyecto ya existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        proyectoRepository.save(proyecto);
        response.put("mensaje", "Proyecto registrado correctamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
