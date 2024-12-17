package com.example.proyectogrupo3.service;

import com.example.proyectogrupo3.model.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface UsuarioService {
    public ResponseEntity<Map<String, Object>> listarUsuarios();
    public ResponseEntity<Map<String, Object>> registrarUsuario(Usuario usuario);

    Optional<Usuario> findByNombre(String nombre);
}
