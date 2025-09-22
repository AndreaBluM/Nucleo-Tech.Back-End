package com.Nucleo.Tech.controller;

import com.Nucleo.Tech.dto.ProductoCreateDto;
import com.Nucleo.Tech.modelo.Categoria;
import com.Nucleo.Tech.modelo.Marca;
import com.Nucleo.Tech.modelo.Producto;
import com.Nucleo.Tech.service.IProductoService;
import com.Nucleo.Tech.respository.IcategoriaRepository;
import com.Nucleo.Tech.respository.ImarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IcategoriaRepository categoriaRepository;

    @Autowired
    private ImarcaRepository marcaRepository;

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerPorId(id);
        return producto.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/crear")
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardar(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        if (!productoService.existePorId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        producto.setId(id);
        Producto productoActualizado = productoService.guardar(producto);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!productoService.existePorId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> obtenerPorCategoria(@PathVariable Long categoriaId) {
        List<Producto> productos = productoService.obtenerPorCategoria(categoriaId);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<Producto>> obtenerPorMarca(@PathVariable Long marcaId) {
        List<Producto> productos = productoService.obtenerPorMarca(marcaId);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping("/crear-con-imagen")
    public ResponseEntity<Producto> crearConImagen(@RequestBody Producto producto) {
        try {
            System.out.println("=== DEBUGGING CREAR CON IMAGEN ===");
            System.out.println("Nombre: " + producto.getNombre());
            System.out.println("Precio: " + producto.getPrecio());
            System.out.println("ImagenBase64 recibida: " + (producto.getImagenBase64() != null ? "SÍ (longitud: " + producto.getImagenBase64().length() + ")" : "NO"));

            // Procesar imagen base64 si existe
            if (producto.getImagenBase64() != null && !producto.getImagenBase64().trim().isEmpty()) {
                String base64Image = producto.getImagenBase64();

                // Remover prefijo si existe
                if (base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }

                try {
                    byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                    String fileName = "producto_" + System.currentTimeMillis() + ".jpg";
                    String uploadDir = "uploads/";
                    Path directoryPath = Paths.get(uploadDir);

                    if (!Files.exists(directoryPath)) {
                        Files.createDirectories(directoryPath);
                    }

                    Files.write(directoryPath.resolve(fileName), imageBytes);
                    producto.setImagenUrl("/uploads/" + fileName);
                    System.out.println("Imagen guardada: " + producto.getImagenUrl());
                } catch (Exception imageException) {
                    System.err.println("Error procesando imagen: " + imageException.getMessage());
                    imageException.printStackTrace();
                }
            }

            // Limpiar imagenBase64 antes de guardar
            producto.setImagenBase64(null);

            Producto productoGuardado = productoService.guardar(producto);
            System.out.println("Producto guardado con ID: " + productoGuardado.getId());
            System.out.println("ImagenUrl final: " + productoGuardado.getImagenUrl());

            return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
