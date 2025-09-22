package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Rol;
import com.Nucleo.Tech.modelo.Usuario;
import com.Nucleo.Tech.respository.IrolRepository;
import com.Nucleo.Tech.respository.IusuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IusuarioRepository usuarioRepository;

    @Autowired
    private IrolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        // Asignar siempre el rol "customer" por defecto
        Rol rolCustomer = rolRepository.findByTipo("customer")
                .orElseThrow(() -> new RuntimeException("Rol 'customer' no encontrado"));

        usuario.setRol(rolCustomer);

        // Encriptar la contraseña antes de guardar
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        return usuarioRepository.save(usuario);
    }
    @Override
    @Transactional

    public Usuario guardarAdmin(Usuario user) {
        if (user.getCorreo() == null || user.getContrasena() == null || user.getNombre() == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        if (usuarioRepository.findByCorreo(user.getCorreo()) != null) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario newUser = new Usuario();
        newUser.setNombre(user.getNombre());
        newUser.setCorreo(user.getCorreo());
        newUser.setContrasena(passwordEncoder.encode(user.getContrasena()));

        Rol rolAdmin = rolRepository.findByTipo("admin")
                .orElseThrow(() -> new RuntimeException("Rol 'admin' no encontrado"));

        newUser.setRol(rolAdmin);

        return usuarioRepository.save(newUser);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorEmail(String email) {
        return Optional.ofNullable(usuarioRepository.findByCorreo(email));
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario autenticar(String correo, String contrasena) {
        Usuario user = usuarioRepository.findByCorreo(correo);
        if (user != null && passwordEncoder.matches(contrasena, user.getContrasena())) {
            return user;
        }
        return null;
    }

    // 🔹 Nuevo usuario con rol "customer"
    @Override
    @Transactional
    public Usuario registerUser(Usuario user) {
        if (user.getCorreo() == null || user.getContrasena() == null || user.getNombre() == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        if (usuarioRepository.findByCorreo(user.getCorreo()) != null) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario newUser = new Usuario();
        newUser.setNombre(user.getNombre());
        newUser.setCorreo(user.getCorreo());
        newUser.setContrasena(passwordEncoder.encode(user.getContrasena()));

        Rol rolCustomer = rolRepository.findByTipo("customer")
                .orElseThrow(() -> new RuntimeException("Rol 'customer' no encontrado"));

        newUser.setRol(rolCustomer);

        return usuarioRepository.save(newUser);
    }

    // 🔹 Integración con Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByCorreo(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Convertir el rol a GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRol().getTipo().toUpperCase()));

        return new org.springframework.security.core.userdetails.User(
                user.getCorreo(),
                user.getContrasena(),
                authorities
        );
    }
}
