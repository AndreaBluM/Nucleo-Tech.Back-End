package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Categoria;
import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    List<Categoria> obtenerTodas();
    Optional<Categoria> obtenerPorId(Long id);
    Categoria guardar(Categoria categoria);
    void eliminar(Long id);
    boolean existePorId(Long id);
    List<Categoria> buscarPorNombre(String nombre);
}
