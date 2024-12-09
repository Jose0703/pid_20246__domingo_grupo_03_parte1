package com.example.proyectogrupo3.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_Tarea")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_tarea;
    private String nombre;
    private String descripcion;
    private String prioridad;

    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;



}
