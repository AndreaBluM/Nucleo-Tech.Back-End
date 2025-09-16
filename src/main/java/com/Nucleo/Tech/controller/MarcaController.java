package com.Nucleo.Tech.controller;

import com.Nucleo.Tech.modelo.Marca;
import com.Nucleo.Tech.service.IMarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    @Autowired
    private IMarcaService marcaService;

    @GetMapping
    public List<Marca> listarMarcas() {
        return marcaService.listarMarcas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> obtenerMarca(@PathVariable Long id) {
        return marcaService.obtenerMarcaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Marca> guardarMarca(@RequestBody Marca marca) {
        Marca nueva = marcaService.guardarMarca(marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMarca(@PathVariable Long id) {
        marcaService.eliminarMarca(id);
        return ResponseEntity.noContent().build();
    }
}

