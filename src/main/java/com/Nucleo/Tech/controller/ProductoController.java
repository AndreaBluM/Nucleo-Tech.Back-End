package com.Nucleo.Tech.controller;

import com.Nucleo.Tech.modelo.Producto;
import com.Nucleo.Tech.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    // Listar todos los productos
    @GetMapping
    public List<Producto> listar() {
        return productoService.listarProductos();
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un producto
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardarProducto(producto));
    }

    // Actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        Optional<Producto> existente = productoService.obtenerProductoPorId(id);
        if (existente.isEmpty()) return ResponseEntity.notFound().build();

        Producto actualizado = existente.get();
        actualizado.setNombre(producto.getNombre());
        actualizado.setPrecio(producto.getPrecio());
        actualizado.setCategoria(producto.getCategoria());
        actualizado.setMarca(producto.getMarca());

        return ResponseEntity.ok(productoService.guardarProducto(actualizado));
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Producto> existente = productoService.obtenerProductoPorId(id);
        if (existente.isEmpty()) return ResponseEntity.notFound().build();

        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar productos por marca
    @GetMapping("/marca/{marcaId}")
    public List<Producto> buscarPorMarca(@PathVariable Long marcaId) {
        return productoService.buscarPorMarca(marcaId);
    }
}
