package com.example.proyectogrupo3.serviceImplement;

import com.example.proyectogrupo3.model.Invitacion;
import com.example.proyectogrupo3.model.Proyecto;
import com.example.proyectogrupo3.repository.ProyectoRepository;
import com.example.proyectogrupo3.repository.UsuarioRepository;
import com.example.proyectogrupo3.repository.InvitacionRepository;
import com.example.proyectogrupo3.service.InvitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvitacionServiceImplement implements InvitacionService {
    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InvitacionRepository invitacionRepository;
    @Override
    public ResponseEntity<Map<String, Object>> listarInvitaciones() {
        Map<String, Object> respuesta = new HashMap<>();
        List<Invitacion> invitaciones = invitacionRepository.findAll();

        if (!invitaciones.isEmpty()) {
            respuesta.put("mensaje", "Lista de invitaciones");
            respuesta.put("invitaciones", invitaciones);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        } else {
            respuesta.put("mensaje", "No existen invitaciones");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> enviarInvitacion(Invitacion invitacion) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            invitacionRepository.save(invitacion);
            respuesta.put("mensaje", "Invitación enviada exitosamente");
            respuesta.put("invitacion", invitacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (Exception e) {
            respuesta.put("mensaje", "Error al enviar la invitación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
}
