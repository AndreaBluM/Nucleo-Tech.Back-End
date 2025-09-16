package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Producto;
import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> listarProductos();
    Optional<Producto> obtenerProductoPorId(Long id);
    Producto guardarProducto(Producto producto);
    void eliminarProducto(Long id);
    List<Producto> buscarPorMarca(Long marcaId);
}
