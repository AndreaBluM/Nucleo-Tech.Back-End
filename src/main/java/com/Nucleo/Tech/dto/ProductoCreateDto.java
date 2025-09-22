package com.Nucleo.Tech.dto;

/**
 * DTO para la creación de productos con soporte para imágenes en base64
 */
public class ProductoCreateDto {
    private String nombre;
    private Double precio;
    private int stock;
    private String descripcion;
    private String especificaciones;
    private String imagenBase64;
    private Long categoriaId;
    private Long marcaId;

    public ProductoCreateDto() {
    }

    public ProductoCreateDto(String nombre, Double precio, int stock, String descripcion,
                            String especificaciones, String imagenBase64, Long categoriaId, Long marcaId) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.especificaciones = especificaciones;
        this.imagenBase64 = imagenBase64;
        this.categoriaId = categoriaId;
        this.marcaId = marcaId;
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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Long marcaId) {
        this.marcaId = marcaId;
    }
}
