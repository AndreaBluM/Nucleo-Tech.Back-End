package com.Nucleo.Tech.controller;

import com.Nucleo.Tech.modelo.Carrito;
import com.Nucleo.Tech.service.ICarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carritos")
@CrossOrigin(origins = "*")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;

    @GetMapping
    public ResponseEntity<List<Carrito>> obtenerTodos() {
        List<Carrito> carritos = carritoService.obtenerTodos();
        return new ResponseEntity<>(carritos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerPorId(@PathVariable Long id) {
        Optional<Carrito> carrito = carritoService.obtenerPorId(id);
        return carrito.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Carrito> crear(@RequestBody Carrito carrito) {
        Carrito nuevoCarrito = carritoService.guardar(carrito);
        return new ResponseEntity<>(nuevoCarrito, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrito> actualizar(@PathVariable Long id, @RequestBody Carrito carrito) {
        if (!carritoService.existePorId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        carrito.setId(id);
        Carrito carritoActualizado = carritoService.guardar(carrito);
        return new ResponseEntity<>(carritoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!carritoService.existePorId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        carritoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
