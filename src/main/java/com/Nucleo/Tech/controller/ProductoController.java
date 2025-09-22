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
    public ResponseEntity<Producto> crear(@RequestBody ProductoCreateDto productoDto) {
        try {
            System.out.println("Recibiendo solicitud para crear producto");

            // Validaciones básicas
            if (productoDto.getNombre() == null || productoDto.getNombre().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Crear un nuevo objeto Producto a partir del DTO
            Producto producto = new Producto();
            producto.setNombre(productoDto.getNombre());
            producto.setPrecio(productoDto.getPrecio());
            producto.setStock(productoDto.getStock());
            producto.setDescripcion(productoDto.getDescripcion());
            producto.setEspecificaciones(productoDto.getEspecificaciones());
            producto.setImagenBase64(productoDto.getImagenBase64());

            // Obtener y establecer la categoría
            if (productoDto.getCategoriaId() != null) {
                Optional<Categoria> categoriaOpt = categoriaRepository.findById(productoDto.getCategoriaId());
                if (categoriaOpt.isPresent()) {
                    producto.setCategoria(categoriaOpt.get());
                } else {
                    System.out.println("Categoría no encontrada con ID: " + productoDto.getCategoriaId());
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                System.out.println("No se especificó categoría");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Obtener y establecer la marca
            if (productoDto.getMarcaId() != null) {
                Optional<Marca> marcaOpt = marcaRepository.findById(productoDto.getMarcaId());
                if (marcaOpt.isPresent()) {
                    producto.setMarca(marcaOpt.get());
                } else {
                    System.out.println("Marca no encontrada con ID: " + productoDto.getMarcaId());
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                System.out.println("No se especificó marca");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Guardar el producto
            Producto productoGuardado = productoService.guardar(producto);
            System.out.println("Producto guardado correctamente con ID: " + productoGuardado.getId());
            return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error al crear producto: " + e.getMessage());
            e.printStackTrace(); // Log para depuración
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    public ResponseEntity<Producto> crearConImagen(@RequestBody ProductoCreateDto productoDto) {
        try {
            System.out.println("Recibiendo solicitud para crear producto con imagen");

            // Validaciones básicas
            if (productoDto.getNombre() == null || productoDto.getNombre().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Crear un nuevo objeto Producto a partir del DTO
            Producto producto = new Producto();
            producto.setNombre(productoDto.getNombre());
            producto.setPrecio(productoDto.getPrecio());
            producto.setStock(productoDto.getStock());
            producto.setDescripcion(productoDto.getDescripcion());
            producto.setEspecificaciones(productoDto.getEspecificaciones());
            
            // Validar y establecer la imagen en base64
            if (productoDto.getImagenBase64() != null && !productoDto.getImagenBase64().isEmpty()) {
                System.out.println("Imagen recibida correctamente con longitud: " + productoDto.getImagenBase64().length());

                // Asegurarse de que la imagen está en formato correcto
                String base64Image = productoDto.getImagenBase64();

                // Si la imagen ya incluye el prefijo "data:image/..." lo dejamos tal cual
                // Si no lo tiene, asumimos que es un base64 puro y lo guardamos directamente
                producto.setImagenBase64(base64Image);
            } else {
                System.out.println("No se recibió imagen o la imagen está vacía");
            }
            
            // Obtener y establecer la categoría
            if (productoDto.getCategoriaId() != null) {
                Optional<Categoria> categoriaOpt = categoriaRepository.findById(productoDto.getCategoriaId());
                if (categoriaOpt.isPresent()) {
                    producto.setCategoria(categoriaOpt.get());
                } else {
                    System.out.println("Categoría no encontrada con ID: " + productoDto.getCategoriaId());
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                System.out.println("No se especificó categoría");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            // Obtener y establecer la marca
            if (productoDto.getMarcaId() != null) {
                Optional<Marca> marcaOpt = marcaRepository.findById(productoDto.getMarcaId());
                if (marcaOpt.isPresent()) {
                    producto.setMarca(marcaOpt.get());
                } else {
                    System.out.println("Marca no encontrada con ID: " + productoDto.getMarcaId());
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                System.out.println("No se especificó marca");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            // Guardar el producto
            Producto productoGuardado = productoService.guardar(producto);
            System.out.println("Producto guardado correctamente con ID: " + productoGuardado.getId());
            return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error al crear producto con imagen: " + e.getMessage());
            e.printStackTrace(); // Log para depuración
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
