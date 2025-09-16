package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.DetalleCarrito;
import java.util.List;
import java.util.Optional;

public interface IDetalleCarritoService {
    List<DetalleCarrito> obtenerTodos();
    Optional<DetalleCarrito> obtenerPorId(Long id);
    DetalleCarrito guardar(DetalleCarrito detalleCarrito);
    void eliminar(Long id);
    boolean existePorId(Long id);
    List<DetalleCarrito> obtenerPorCarritoId(Long carritoId);
    Optional<DetalleCarrito> buscarDetalleCarritoPorProductoYCarrito(Long productoId, Long carritoId);
}
