package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.DetalleCarrito;
import java.util.List;
import java.util.Optional;

public interface IDetalleCarritoService {
    List<DetalleCarrito> listarDetallesCarrito();
    Optional<DetalleCarrito> obtenerDetalleCarritoPorId(Long id);
    DetalleCarrito guardarDetalleCarrito(DetalleCarrito detalleCarrito);
    void eliminarDetalleCarrito(Long id);
}
