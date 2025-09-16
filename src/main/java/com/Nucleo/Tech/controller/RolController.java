package com.Nucleo.Tech.controller;

import com.Nucleo.Tech.modelo.Rol;
import com.Nucleo.Tech.service.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private IRolService rolService;

    @GetMapping
    public ResponseEntity<List<Rol>> obtenerTodos() {
        List<Rol> roles = rolService.obtenerTodos();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerPorId(@PathVariable Long id) {
        Optional<Rol> rol = rolService.obtenerPorId(id);
        return rol.map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Rol> crear(@RequestBody Rol rol) {
        // Verificar si ya existe un rol con ese nombre
        Optional<Rol> rolExistente = rolService.buscarPorNombre(rol.getTipo());
        if (rolExistente.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Rol nuevoRol = rolService.guardar(rol);
        return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizar(@PathVariable Long id, @RequestBody Rol rol) {
        if (!rolService.existePorId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Verificar si otro rol ya tiene ese nombre
        Optional<Rol> rolExistente = rolService.buscarPorNombre(rol.getTipo());
        if (rolExistente.isPresent() && !rolExistente.get().getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        rol.setId(id);
        Rol rolActualizado = rolService.guardar(rol);
        return new ResponseEntity<>(rolActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!rolService.existePorId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        rolService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Rol> buscarPorNombre(@PathVariable String nombre) {
        Optional<Rol> rol = rolService.buscarPorNombre(nombre);
        return rol.map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
