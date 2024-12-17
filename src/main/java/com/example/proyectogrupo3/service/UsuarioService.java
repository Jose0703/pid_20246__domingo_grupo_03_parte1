package com.example.proyectogrupo3.service;

import com.example.proyectogrupo3.model.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UsuarioService {
    public ResponseEntity<Map<String, Object>> listarUsuarios();
    public ResponseEntity<Map<String, Object>> registrarUsuario(Usuario usuario);


}
