package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Carrito;
import java.util.List;
import java.util.Optional;

public interface ICarritoService {
    List<Carrito> listarCarritos();
    Optional<Carrito> obtenerCarritoPorId(Long id);
    Carrito guardarCarrito(Carrito carrito);
    void eliminarCarrito(Long id);
    Optional<Carrito> obtenerCarritoDeUsuario(Long usuarioId);
}
