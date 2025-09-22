package com.Nucleo.Tech.controller;

import com.Nucleo.Tech.JwtUtil;
import com.Nucleo.Tech.dto.LoginResponse;
import com.Nucleo.Tech.dto.UserDto;
import com.Nucleo.Tech.modelo.Usuario;
import com.Nucleo.Tech.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔹 Registro de usuarios
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Usuario usuario) {
        try {
            usuarioService.registerUser(usuario);
            return ResponseEntity.ok("Usuario registrado con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody Usuario usuario) {
        try {
            usuarioService.guardarAdmin(usuario);
            return ResponseEntity.ok("Usuario registrado con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // 🔹 Login con correo
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        Usuario usuario = usuarioService.buscarPorEmail(userDto.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (passwordEncoder.matches(userDto.getPassword(), usuario.getContrasena())) {
            String token = jwtUtil.generateToken(usuario.getCorreo());

            // Devuelves token + info del usuario
            return ResponseEntity.ok(
                    new LoginResponse(
                            token,
                            usuario.getId(),
                            usuario.getNombre(),
                            usuario.getCorreo(),
                            usuario.getRol().getTipo()  // 👈 Aquí envías el rol
                    )
            );
        }

        return ResponseEntity.status(401).body("Credenciales inválidas");
    }

    // 🔹 Ejemplo de endpoint protegido
    @GetMapping("/resource")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> getProtectedResource() {
        return ResponseEntity.ok("Este es un recurso protegido!");
    }


    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        return usuario.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (!usuarioService.existePorId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Usuario> usuarioExistente = usuarioService.buscarPorEmail(usuario.getCorreo());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        usuario.setId(id);
        Usuario usuarioActualizado = usuarioService.guardar(usuario);
        return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!usuarioService.existePorId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        usuarioService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);
        return usuario.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/autenticar")
    public ResponseEntity<Usuario> autenticar(@RequestBody Usuario usuario) {
        Usuario usuarioAutenticado = usuarioService.autenticar(usuario.getCorreo(), usuario.getContrasena());
        if (usuarioAutenticado != null) {
            return new ResponseEntity<>(usuarioAutenticado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}

