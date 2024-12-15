package com.example.proyectogrupo3.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_Tarea")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea", nullable = false)
    private Long id_Tarea;
    private String nombre;
    private String descripcion;
    private String prioridad;
    @Column(name = "fecha_vencimiento", nullable = false)
    private Date fechavencimineto;

    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;


    @Transient
    private String nombreProyecto;

}
