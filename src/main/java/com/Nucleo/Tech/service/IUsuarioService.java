package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> obtenerTodos();
    Optional<Usuario> obtenerPorId(Long id);
    Usuario guardar(Usuario usuario);
    Usuario guardarAdmin(Usuario usuario);
    void eliminar(Long id);
    boolean existePorId(Long id);
    Optional<Usuario> buscarPorEmail(String email);
    Usuario autenticar(String correo, String contrasena);

    Usuario registerUser(Usuario user); // registrar un nuevo usuario con rol "customer"
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException; // integración con Spring Security
}

