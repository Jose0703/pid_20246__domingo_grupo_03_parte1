package com.example.proyectogrupo3.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

@Data
@Table(name = "tb_tarea")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_tarea;
    private String nombre;
    private String descripcion;
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    private String desarrollado;
    private String prioridad;
    @ElementCollection
    @CollectionTable(name = "comentarios_tarea", joinColumns = @JoinColumn(name = "id_tarea"))
    @Column(name = "comentario")
    private List<String> comentarios = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;



    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // Usuario asignado a esta tarea

    @OneToMany(mappedBy = "tarea")
    private List<TareaUsuarioRol> tareaUsuarioRoles; // Relación con TareaUsuarioRol











}
