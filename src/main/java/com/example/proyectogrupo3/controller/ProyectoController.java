package com.example.proyectogrupo3.controller;

import com.example.proyectogrupo3.model.Proyecto;
import com.example.proyectogrupo3.service.ProyectoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/proyecto")
@CrossOrigin(origins = "https://localhost:4200/")
@AllArgsConstructor
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> listProyecto() {
        return proyectoService.listarProyectos();
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> addProyecto(@RequestBody Proyecto proyecto, @RequestParam Long id_usuario) {
        return  proyectoService.registrarProyecto(proyecto, id_usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editarProyecto(@PathVariable Long id, @RequestBody Proyecto proyecto) {
        return proyectoService.actualizarProyecto(proyecto, id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarProyecto(@PathVariable Long id) {
        return proyectoService.eliminarProyecto(id);
    }
}