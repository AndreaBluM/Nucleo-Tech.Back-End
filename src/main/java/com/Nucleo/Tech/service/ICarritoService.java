package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Carrito;
import java.util.List;
import java.util.Optional;

public interface ICarritoService {
    List<Carrito> obtenerTodos();
    Optional<Carrito> obtenerPorId(Long id);
    Carrito guardar(Carrito carrito);
    void eliminar(Long id);
    boolean existePorId(Long id);
    Optional<Carrito> buscarPorUsuarioId(Long usuarioId);
}
