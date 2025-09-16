package com.Nucleo.Tech.controller;

import com.Nucleo.Tech.modelo.Carrito;
import com.Nucleo.Tech.service.ICarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;

    @GetMapping
    public List<Carrito> listarCarritos() {
        return carritoService.listarCarritos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable Long id) {
        return carritoService.obtenerCarritoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Carrito> guardarCarrito(@RequestBody Carrito carrito) {
        Carrito nuevo = carritoService.guardarCarrito(carrito);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Long id) {
        carritoService.eliminarCarrito(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Carrito> obtenerCarritoDeUsuario(@PathVariable Long usuarioId) {
        return carritoService.obtenerCarritoDeUsuario(usuarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
