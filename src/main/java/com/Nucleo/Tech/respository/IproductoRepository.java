package com.Nucleo.Tech.respository;

import com.Nucleo.Tech.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IproductoRepository extends JpaRepository<Producto, Long> {
}
