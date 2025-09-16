package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Rol;
import java.util.List;
import java.util.Optional;

public interface IRolService {
    List<Rol> obtenerTodos();
    Optional<Rol> obtenerPorId(Long id);
    Rol guardar(Rol rol);
    void eliminar(Long id);
    boolean existePorId(Long id);
    Optional<Rol> buscarPorNombre(String nombre);
}
