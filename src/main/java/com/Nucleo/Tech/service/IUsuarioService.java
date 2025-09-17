package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> obtenerTodos();
    Optional<Usuario> obtenerPorId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    boolean existePorId(Long id);
    Optional<Usuario> buscarPorEmail(String email);
    Usuario autenticar(String correo, String contrasena);
}
