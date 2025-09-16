package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Producto;
import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> obtenerTodos();
    Optional<Producto> obtenerPorId(Long id);
    Producto guardar(Producto producto);
    void eliminar(Long id);
    boolean existePorId(Long id);
    // Métodos adicionales específicos si los necesitas
    List<Producto> obtenerPorCategoria(Long categoriaId);
    List<Producto> obtenerPorMarca(Long marcaId);
    List<Producto> buscarPorNombre(String nombre);
}
