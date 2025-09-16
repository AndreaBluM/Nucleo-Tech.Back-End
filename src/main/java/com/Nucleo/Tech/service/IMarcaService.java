package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Marca;
import java.util.List;
import java.util.Optional;

public interface IMarcaService {
    List<Marca> listarMarcas();
    Optional<Marca> obtenerMarcaPorId(Long id);
    Marca guardarMarca(Marca marca);
    void eliminarMarca(Long id);
}
