package com.Nucleo.Tech.controller;

import com.Nucleo.Tech.modelo.DetalleCarrito;
import com.Nucleo.Tech.service.IDetalleCarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalles-carrito")
@CrossOrigin(origins = "*")
public class DetalleCarritoController {

    @Autowired
    private IDetalleCarritoService detalleCarritoService;

    @GetMapping
    public ResponseEntity<List<DetalleCarrito>> obtenerTodos() {
        List<DetalleCarrito> detalles = detalleCarritoService.obtenerTodos();
        return new ResponseEntity<>(detalles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCarrito> obtenerPorId(@PathVariable Long id) {
        Optional<DetalleCarrito> detalle = detalleCarritoService.obtenerPorId(id);
        return detalle.map(d -> new ResponseEntity<>(d, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<DetalleCarrito> crear(@RequestBody DetalleCarrito detalleCarrito) {
        // Verificar si ya existe el producto en el carrito
        Optional<DetalleCarrito> detalleExistente = detalleCarritoService.buscarDetalleCarritoPorProductoYCarrito(
                detalleCarrito.getProducto().getId(),
                detalleCarrito.getCarrito().getId()
        );

        if (detalleExistente.isPresent()) {
            // Si ya existe, actualizar la cantidad en lugar de crear uno nuevo
            DetalleCarrito detalle = detalleExistente.get();
            detalle.setCantidad(detalle.getCantidad() + detalleCarrito.getCantidad());
            DetalleCarrito detalleActualizado = detalleCarritoService.guardar(detalle);
            return new ResponseEntity<>(detalleActualizado, HttpStatus.OK);
        }

        // Si no existe, crear uno nuevo
        DetalleCarrito nuevoDetalle = detalleCarritoService.guardar(detalleCarrito);
        return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleCarrito> actualizar(@PathVariable Long id, @RequestBody DetalleCarrito detalleCarrito) {
        if (!detalleCarritoService.existePorId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        detalleCarrito.setId(id);
        DetalleCarrito detalleActualizado = detalleCarritoService.guardar(detalleCarrito);
        return new ResponseEntity<>(detalleActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!detalleCarritoService.existePorId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        detalleCarritoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/carrito/{carritoId}")
    public ResponseEntity<List<DetalleCarrito>> obtenerPorCarritoId(@PathVariable Long carritoId) {
        List<DetalleCarrito> detalles = detalleCarritoService.obtenerPorCarritoId(carritoId);
        return new ResponseEntity<>(detalles, HttpStatus.OK);
    }

    @GetMapping("/carrito/{carritoId}/producto/{productoId}")
    public ResponseEntity<DetalleCarrito> buscarPorProductoYCarrito(
            @PathVariable Long carritoId,
            @PathVariable Long productoId) {
        Optional<DetalleCarrito> detalle = detalleCarritoService.buscarDetalleCarritoPorProductoYCarrito(productoId, carritoId);
        return detalle.map(d -> new ResponseEntity<>(d, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
