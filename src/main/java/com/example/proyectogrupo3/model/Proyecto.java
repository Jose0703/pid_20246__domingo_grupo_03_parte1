package com.example.proyectogrupo3.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_proyecto")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
}
