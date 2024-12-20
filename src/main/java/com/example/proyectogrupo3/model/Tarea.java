package com.example.proyectogrupo3.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaVencimiento;
    private String desarrollado;
    @Enumerated(EnumType.STRING) // Esto guarda el nombre del Enum como texto
    private Prioridad prioridad;

    @ElementCollection
    @CollectionTable(name = "tb_tarea_comentario", joinColumns = @JoinColumn(name = "id_tarea"))
    @Column(name = "comentario")
    private List<String> comentarios = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = true )
    private Usuario asignadoA;

}
