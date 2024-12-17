package com.example.proyectogrupo3.controller;


import com.example.proyectogrupo3.model.Tarea;

import com.example.proyectogrupo3.service.TareaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tarea")
@CrossOrigin("https://localhost:4200/")
@AllArgsConstructor
public class TareaController {

    @Autowired
    private TareaService service;



    @GetMapping
    public ResponseEntity<Map<String,Object>> listTarea(){
        return service.listarTarea();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> listaPorID(@PathVariable Long id){
        return service.listarTareaPorId(id);
    }

    @PostMapping("/tareas")
    public ResponseEntity<Map<String, Object>> agregarTarea(@Validated @RequestBody Tarea tarea){
        return service.registrarTarea(tarea);
    }

    @PostMapping("/{idTarea}/comentarios")
    public ResponseEntity<Tarea> agregarComentario(@PathVariable Long idTarea, @RequestBody String comentario) {
        try {
            Tarea tarea = service.agregarComentario(idTarea, comentario);
            return ResponseEntity.ok(tarea); // Devuelve la tarea con el nuevo comentario
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Si la tarea no se encuentra
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTarea(@RequestBody Tarea tarea,@PathVariable Long id){
        return service.actualizarTarea(tarea,id);
    }

    @PostMapping("/asignar-rol")
    public ResponseEntity<String> asignarRolATarea(@RequestParam Long id_tarea, @RequestParam Long IdUsuario, @RequestParam String rol) {
        try {
            service.asignarRolATarea(id_tarea, IdUsuario, rol); // Llama al servicio que asigna el rol
            return ResponseEntity.ok("Rol asignado a la tarea correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }



    @DeleteMapping
            ("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id){
        return service.eliminarTarea(id);
    }

}
