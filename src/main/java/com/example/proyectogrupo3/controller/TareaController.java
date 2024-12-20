package com.example.proyectogrupo3.controller;

import com.example.proyectogrupo3.model.Tarea;
import com.example.proyectogrupo3.service.TareaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tarea")
@CrossOrigin("https://localhost:4200/")
@AllArgsConstructor
public class TareaController {

    @Autowired
    private TareaService service;



    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> listaPorID(@PathVariable Long id){
        return service.listarTareaPorId(id);
    }

    @PostMapping("tarea")
    public ResponseEntity<Map<String, Object>> agregarTarea(@Validated @RequestBody Tarea tarea) {
        return service.registrarTarea(tarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTarea(@RequestBody Tarea tarea,@PathVariable Long id){
        return service.actualizarTarea(tarea,id);
    }

    @DeleteMapping
            ("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id){
        return service.eliminarTarea(id);
    }

    @PostMapping("/{idTarea}/comentarios")
    public ResponseEntity<Tarea> agregarComentario(@PathVariable Long idTarea, @RequestBody String comentario) {
        try {
            Tarea tarea = service.agregarComentario(idTarea, comentario);
            return ResponseEntity.ok(tarea);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/rol")
    public ResponseEntity<String> asignarRolATarea(@RequestParam Long id_tarea, @RequestParam Long id_usuario, @RequestParam String rol) {
        try {
            service.asignarRolATarea(id_tarea, id_usuario, rol); // Llama al servicio que asigna el rol
            return ResponseEntity.ok("Rol asignado a la tarea correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTareasConUsuariosYRoles() {
        List<Tarea> tareasConRoles = service.obtenerTareasConUsuariosYRoles();
        return ResponseEntity.ok(tareasConRoles);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Tarea> guardarTarea(@RequestBody Tarea tarea) {
        try {
            Tarea savedTarea = service.guardarTarea(tarea); // Solo se llama al servicio
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTarea);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Si hay error al guardar la tarea
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




}
