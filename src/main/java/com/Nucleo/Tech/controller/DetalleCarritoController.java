package com.Nucleo.Tech.controller;

import com.Nucleo.Tech.modelo.DetalleCarrito;
import com.Nucleo.Tech.service.IDetalleCarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles-carrito")
public class DetalleCarritoController {

    @Autowired
    private IDetalleCarritoService detalleCarritoService;

    @GetMapping
    public List<DetalleCarrito> listarDetalles() {
        return detalleCarritoService.listarDetallesCarrito();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCarrito> obtenerDetalle(@PathVariable Long id) {
        return detalleCarritoService.obtenerDetalleCarritoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DetalleCarrito> guardarDetalle(@RequestBody DetalleCarrito detalle) {
        DetalleCarrito nuevo = detalleCarritoService.guardarDetalleCarrito(detalle);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Long id) {
        detalleCarritoService.eliminarDetalleCarrito(id);
        return ResponseEntity.noContent().build();
    }
}
