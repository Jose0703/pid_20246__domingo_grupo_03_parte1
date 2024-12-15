package com.example.proyectogrupo3.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_proyecto")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_proyecto")
    private Long idProyecto;
    @Column(unique = true, nullable = false)
    private String nombre;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Transient
    private String nombreUsuario;

}
