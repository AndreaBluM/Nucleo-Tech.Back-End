package com.Nucleo.Tech.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
public class DetalleCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;
    private double precioTotal;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    public DetalleCarrito() {
    }

    public DetalleCarrito(Long id, int cantidad, double precioTotal, Producto producto, Carrito carrito) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.producto = producto;
        this.carrito = carrito;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }
}
