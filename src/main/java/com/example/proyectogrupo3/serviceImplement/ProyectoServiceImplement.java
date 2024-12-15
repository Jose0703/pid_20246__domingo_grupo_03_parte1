package com.example.proyectogrupo3.serviceImplement;

import com.example.proyectogrupo3.model.Proyecto;
import com.example.proyectogrupo3.model.Usuario;
import com.example.proyectogrupo3.repository.ProyectoRepository;
import com.example.proyectogrupo3.repository.UsuarioRepository;
import com.example.proyectogrupo3.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Service
public class ProyectoServiceImplement implements ProyectoService {
    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public ResponseEntity<Map<String, Object>> listarProyectos() {
        Map<String, Object> response = new HashMap<>();
        List<Proyecto> proyectos = proyectoRepository.findAll();

        if (proyectos.isEmpty()) {
            response.put("mensaje", "No se encontraron proyectos");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } else {
            proyectos.sort(Comparator.comparingLong(Proyecto::getIdProyecto));

            List<Map<String, Object>> proyectosList = new ArrayList<>();
            for (Proyecto proyecto : proyectos) {
                Optional<Usuario> usuarioOpt = usuarioRepository.findById(proyecto.getUsuario().getId());
                if (usuarioOpt.isPresent()) {
                    Usuario usuario = usuarioOpt.get();
                    Map<String, Object> proyectoMap = new HashMap<>();
                    proyectoMap.put("idProyecto", proyecto.getIdProyecto());
                    proyectoMap.put("nombre", proyecto.getNombre());
                    proyectoMap.put("descripcion", proyecto.getDescripcion());
                    proyectoMap.put("nombreUsuario", usuario.getNombre());
                    proyectosList.add(proyectoMap);
                } else {
                    response.put("advertencia", "Usuario no encontrado para el proyecto ID " + proyecto.getIdProyecto());
                }
            }
            response.put("mensaje", "Proyectos encontrados");
            response.put("proyectos", proyectosList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> registrarProyecto(Proyecto proyecto, @RequestParam(required = false) Long id_usuario){
        System.out.println("Entrando al método registrarProyecto");
        Map<String, Object> response = new HashMap<>();

        if (proyectoRepository.existsByNombre(proyecto.getNombre())) {
            response.put("mensaje", "El proyecto ya existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        Usuario usuario;

        if (id_usuario == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String nombreUsuario = authentication.getName();
            usuario = usuarioRepository.findByNombre(nombreUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        } else {
            usuario = usuarioRepository.findById(id_usuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        }

        System.out.println("El proyecto está siendo registrado por: " + usuario.getNombre());
        proyecto.setUsuario(usuario);
        proyectoRepository.save(proyecto);

        response.put("mensaje", "Proyecto registrado correctamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @Override
    public ResponseEntity<Map<String, Object>> actualizarProyecto(Proyecto proyecto, Long id_proyecto) {
        Map<String, Object> response = new HashMap<>();
        Optional<Proyecto> proyectoExistente = proyectoRepository.findById(id_proyecto);
        if (proyectoExistente.isPresent()) {
            Proyecto proyectoActualizada = proyectoExistente.get();
            if (proyectoRepository.existsByNombre(proyecto.getNombre())) {
                response.put("mensaje", "El proyecto ya existe");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
            proyectoActualizada.setNombre(proyecto.getNombre());
            proyectoActualizada.setDescripcion(proyecto.getDescripcion());
            proyectoRepository.save(proyectoActualizada);
            response.put("mensaje", "Proyecto actualizado correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            response.put("mensaje", "El proyecto no existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> eliminarProyecto(Long id) {
        return null;
    }

}
