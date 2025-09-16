package com.Nucleo.Tech.modelo;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;


    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Producto> producto;


    public Categoria() {
    }

    public Categoria(Long id, String nombre, List<Producto> producto) {
        this.id = id;
        this.nombre = nombre;
        this.producto = producto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Producto> getProducto() {
        return producto;
    }

    public void setProducto(List<Producto> producto) {
        this.producto = producto;
    }
}
