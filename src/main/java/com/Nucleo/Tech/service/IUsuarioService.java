package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listarUsuarios();
    Optional<Usuario> obtenerUsuarioPorId(Long id);
    Usuario guardarUsuario(Usuario usuario);
    void eliminarUsuario(Long id);
    Optional<Usuario> buscarPorEmail(String email);
}
