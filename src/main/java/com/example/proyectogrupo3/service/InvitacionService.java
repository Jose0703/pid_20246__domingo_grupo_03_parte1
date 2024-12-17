package com.example.proyectogrupo3.service;

import com.example.proyectogrupo3.model.Invitacion;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface InvitacionService {
    ResponseEntity<Map<String, Object>> listarInvitaciones();
    ResponseEntity<Map<String, Object>> enviarInvitacion(Invitacion invitacion);
}
