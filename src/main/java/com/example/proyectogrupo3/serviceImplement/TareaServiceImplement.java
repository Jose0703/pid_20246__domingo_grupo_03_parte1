package com.example.proyectogrupo3.serviceImplement;

import com.example.proyectogrupo3.model.Proyecto;
import com.example.proyectogrupo3.model.Tarea;
import com.example.proyectogrupo3.model.TareaUsuarioRol;
import com.example.proyectogrupo3.model.Usuario;
import com.example.proyectogrupo3.repository.ProyectoRepository;
import com.example.proyectogrupo3.repository.TareaRepository;
import com.example.proyectogrupo3.repository.TareaUsuarioRolRepository;
import com.example.proyectogrupo3.repository.UsuarioRepository;
import com.example.proyectogrupo3.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TareaServiceImplement implements TareaService {

    @Autowired
    private TareaRepository dao;
    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TareaUsuarioRolRepository tareaUsuarioRolRepository;


    @Override
    public ResponseEntity<Map<String, Object>> listarTarea() {
        Map<String, Object> respuesta = new HashMap<>();
        List<Tarea> tareas = dao.findAll();

        if (!tareas.isEmpty()){
            respuesta.put("mensaje", "Lista de tareas");
            respuesta.put("tareas", tareas);
            respuesta.put("status", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.put("mensaje", "No existen registros");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> listarTareaPorId(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Tarea> tareas = dao.findById(id);

        if (!tareas.isEmpty()){
            respuesta.put("tareas", tareas);
            respuesta.put("mensaje", "busqueda correcta");
            respuesta.put("status", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.put("mensaje", "No existen registros con el ID: " + id);
            respuesta.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    public ResponseEntity<Map<String, Object>> registrarTarea(Tarea tarea) {
        Map<String, Object> respuesta = new HashMap<>();

        // Verificar si el Proyecto existe
        Proyecto proyecto = tarea.getProyecto();

        try {
            // Verificar si el Proyecto existe en la base de datos antes de asociarlo
            if (proyecto.getIdProyecto() != null) {
                Proyecto finalProyecto = proyecto;
                proyecto = proyectoRepository.findById(proyecto.getIdProyecto())
                        .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + finalProyecto.getIdProyecto()));
            } else {
                // Si no tiene id, significa que es nuevo, se puede guardar el proyecto (si necesario)
                proyectoRepository.save(proyecto);
            }

            // Asignar el Proyecto a la Tarea
            tarea.setProyecto(proyecto);

            // Verificar si la tarea ya existe
            if (dao.existsByNombre(tarea.getNombre())) {
                respuesta.put("mensaje", "Tarea existente con el mismo nombre");
                respuesta.put("status", HttpStatus.CONFLICT);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                try {
                    dao.save(tarea);  // Guardar la tarea
                    respuesta.put("Tarea", tarea);
                    respuesta.put("mensaje", "Se añadió correctamente la tarea");
                    respuesta.put("status", HttpStatus.CREATED);
                    return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
                } catch (Exception e) {
                    respuesta.put("mensaje", "Error interno del servidor: " + e.getMessage());
                    respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
                }
            }
        } catch (RuntimeException e) {
            respuesta.put("mensaje", "Error al encontrar el proyecto: " + e.getMessage());
            respuesta.put("status", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }



    @Override
    public ResponseEntity<Map<String, Object>> actualizarTarea(Tarea tarea, Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Tarea> tareaExiste = dao.findById(id);
        if(tareaExiste.isPresent()){
            Tarea tareaGet = tareaExiste.get();

            if(dao.existsByNombre(tarea.getNombre())){
                respuesta.put("mensaje", "Ya existe una tarea con este nombre");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            }

            tareaGet.setNombre(tarea.getNombre());
            tareaGet.setDescripcion(tarea.getDescripcion());
            tareaGet.setFechaVencimiento(tarea.getFechaVencimiento());
            tareaGet.setDesarrollado(tarea.getDesarrollado());
            tareaGet.setPrioridad(tarea.getPrioridad());
            tareaGet.setProyecto(tarea.getProyecto());
            dao.save(tarea);
            respuesta.put("tarea", tareaGet);
            respuesta.put("mensaje", "Datos de la tarea modificada");
            respuesta.put("status", HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }else{
            respuesta.put("mensaje", "sin registros con ID: "+ id);
            respuesta.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> eliminarTarea(Long id) {
        Map<String,Object> respuesta = new HashMap<>();
        Optional<Tarea> tareaExiste = dao.findById(id);
        if (tareaExiste.isPresent()) {
            Tarea tarea = tareaExiste.get();
            dao.delete(tarea);
            respuesta.put("mensaje", "Eliminado correctamente");
            respuesta.put("status", HttpStatus.NO_CONTENT);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
        }else {
            respuesta.put("mensaje", "Sin registros con ID: " + id);
            respuesta.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Override
    public Tarea agregarComentario(Long idTarea, String comentario) {
        Tarea tarea = dao.findById(idTarea)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        tarea.getComentarios().add(comentario);
        return dao.save(tarea);
    }

    @Override
    public void asignarRolATarea(Long idTarea, Long idUsuario, String rol) {
        Optional<Tarea> tareaOptional = dao.findById(idTarea);
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

        if (tareaOptional.isPresent() && usuarioOptional.isPresent()) {
            Tarea tarea = tareaOptional.get();
            Usuario usuario = usuarioOptional.get();

            // Crear una nueva instancia de TareaUsuarioRol
            TareaUsuarioRol tareaUsuarioRol = new TareaUsuarioRol();
            tareaUsuarioRol.setTarea(tarea);
            tareaUsuarioRol.setUsuario(usuario);
            tareaUsuarioRol.setRol(rol);

            // Guardar la relación en la base de datos
            tareaUsuarioRolRepository.save(tareaUsuarioRol);
        } else {
            throw new RuntimeException("Tarea o Usuario no encontrados");
        }
    }
    @Override
    public List<Tarea> obtenerTareasConUsuariosYRoles() {
        List<Tarea> tareas = dao.findAll();  // Obtener todas las tareas

        for (Tarea tarea : tareas) {
            // Para cada tarea, cargar los usuarios con su rol
            List<TareaUsuarioRol> tareaUsuarioRoles = tareaUsuarioRolRepository.findByTarea(tarea);

            for (TareaUsuarioRol tareaUsuarioRol : tareaUsuarioRoles) {
                // Asociamos el rol al usuario
                Usuario usuario = tareaUsuarioRol.getUsuario();
                usuario.setRol(tareaUsuarioRol.getRol());  // Establecemos el rol en el usuario
            }
        }
        return tareas;
    }

    @Override
    public Tarea guardarTarea(Tarea tarea) {
        // Verifica si el usuario está asignado a la tarea y existe en la base de datos
        if (tarea.getAsignadoA() != null) {
            Usuario usuario = usuarioRepository.findById(tarea.getAsignadoA().getId()).orElse(null);
            if (usuario != null) {
                tarea.setAsignadoA(usuario);
            } else {
                throw new RuntimeException("Usuario no encontrado.");
            }
        }

        // Verifica si el proyecto está asignado y existe en la base de datos
        if (tarea.getProyecto() != null) {
            Proyecto proyecto = proyectoRepository.findById(tarea.getProyecto().getIdProyecto()).orElse(null);
            if (proyecto != null) {
                tarea.setProyecto(proyecto);
            } else {
                throw new RuntimeException("Proyecto no encontrado.");
            }
        }

        // Guarda la tarea en la base de datos
        return dao.save(tarea);
    }


}

