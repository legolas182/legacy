package com.legacy.legacy.model;

import com.legacy.legacy.model.enums.TipoContacto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contactos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contactos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contacto", nullable = false)
    private TipoContacto tipoContacto;
    
    private String email;
    
    private String telefono;
    
    private String direccion;
    
    private String empresa;
    
    private String nit;
    
    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Roles rol;
}

