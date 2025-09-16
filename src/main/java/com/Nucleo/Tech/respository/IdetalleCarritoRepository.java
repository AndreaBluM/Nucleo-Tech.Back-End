package com.Nucleo.Tech.respository;

import com.Nucleo.Tech.modelo.DetalleCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdetalleCarritoRepository extends JpaRepository<DetalleCarrito, Long> {
}
