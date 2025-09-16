package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Marca;
import java.util.List;
import java.util.Optional;

public interface IMarcaService {
    List<Marca> obtenerTodas();
    Optional<Marca> obtenerPorId(Long id);
    Marca guardar(Marca marca);
    void eliminar(Long id);
    boolean existePorId(Long id);
    List<Marca> buscarPorNombre(String nombre);
}
