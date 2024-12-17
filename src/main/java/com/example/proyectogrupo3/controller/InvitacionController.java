package com.example.proyectogrupo3.controller;

import com.example.proyectogrupo3.model.Invitacion;
import com.example.proyectogrupo3.service.InvitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/invitaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class InvitacionController {

    @Autowired
    private InvitacionService invitacionService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarInvitaciones() {
        return invitacionService.listarInvitaciones();
    }

    @PostMapping("/enviar")
    public ResponseEntity<Map<String, Object>> enviarInvitacion(@RequestBody Invitacion invitacion) {
        return invitacionService.enviarInvitacion(invitacion);
    }
}
