package com.example.proyectogrupo3.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_usuario")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String direccion;
    private Long id_rol;
    private String estado;

    public boolean validarDatos() {

        if (nombre.isEmpty() || apellido.isEmpty()) {
            System.out.println("Nombre y apellido son obligatorios");
            return false;
        }

        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(emailRegex)) {
            System.out.println("Correo electrónico inválido");
            return false;
        }


        if (password.length() < 8) {
            System.out.println("La contraseña debe tener al menos 8 caracteres");
            return false;
        }


        if (nombre.length() < 6 || nombre.length() > 30) {
            System.out.println("El nombre de usuario debe tener entre 6 y 30 caracteres");
            return false;
        }

        return true;
    }
}