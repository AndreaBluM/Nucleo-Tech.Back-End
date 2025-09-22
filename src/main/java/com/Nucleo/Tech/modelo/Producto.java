package com.Nucleo.Tech.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    private int stock;
    private String descripcion;
    private String especificaciones;

    @Lob
    @JsonProperty("imagenBase64")
    private String imagenBase64;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @OneToMany(mappedBy = "producto")
    private java.util.List<DetalleCarrito> detalles;

    public Producto() {
    }

    public Producto(Long id, String nombre, Double precio, int stock, String especificaciones, String descripcion, String imagenBase64, Categoria categoria, Marca marca, List<DetalleCarrito> detalles) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.especificaciones = especificaciones;
        this.descripcion = descripcion;
        this.imagenBase64 = imagenBase64;
        this.categoria = categoria;
        this.marca = marca;
        this.detalles = detalles;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public List<DetalleCarrito> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCarrito> detalles) {
        this.detalles = detalles;
    }
}
