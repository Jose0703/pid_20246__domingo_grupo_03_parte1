package com.example.proyectogrupo3.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "tb_usuario")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Long id;
    @Column(unique = true, nullable = false)
    private String nombre;
    private String apellido;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Column(unique = true, nullable = false)
    private String dni;
    private String telefono;
    private String direccion;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rol;

}