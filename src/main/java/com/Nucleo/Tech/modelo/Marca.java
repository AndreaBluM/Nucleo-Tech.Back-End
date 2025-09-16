package com.Nucleo.Tech.modelo;

import jakarta.persistence.*;

@Entity
public class Marca {
    @Id
    private Long id;
    private String nombre;

    @OneToMany
    @JoinColumn(name = "marca_id")
    private Producto producto;

}
