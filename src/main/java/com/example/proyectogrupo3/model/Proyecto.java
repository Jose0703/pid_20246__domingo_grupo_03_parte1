package com.example.proyectogrupo3.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "tb_proyecto")
@EntityListeners(AuditingEntityListener.class)
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
  
    @PostLoad
    public void setNombreUsuario() {
        this.nombreUsuario = this.usuario != null ? this.usuario.getNombre() : null;
    }
}
