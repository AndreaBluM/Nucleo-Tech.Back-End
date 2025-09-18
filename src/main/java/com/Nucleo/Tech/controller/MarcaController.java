package com.Nucleo.Tech.controller;

import com.Nucleo.Tech.modelo.Marca;
import com.Nucleo.Tech.service.IMarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/marcas")
@CrossOrigin(origins = "*")
public class MarcaController {

    @Autowired
    private IMarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<Marca>> obtenerTodas() {
        List<Marca> marcas = marcaService.obtenerTodas();
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> obtenerPorId(@PathVariable Long id) {
        Optional<Marca> marca = marcaService.obtenerPorId(id);
        return marca.map(m -> new ResponseEntity<>(m, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/crear")
    public ResponseEntity<Marca> crear(@RequestBody Marca marca) {
        Marca nuevaMarca = marcaService.guardar(marca);
        return new ResponseEntity<>(nuevaMarca, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> actualizar(@PathVariable Long id, @RequestBody Marca marca) {
        if (!marcaService.existePorId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        marca.setId(id);
        Marca marcaActualizada = marcaService.guardar(marca);
        return new ResponseEntity<>(marcaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!marcaService.existePorId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        marcaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Marca>> buscarPorNombre(@RequestParam String nombre) {
        List<Marca> marcas = marcaService.buscarPorNombre(nombre);
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }
}
