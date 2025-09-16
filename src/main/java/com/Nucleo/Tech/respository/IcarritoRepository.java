package com.Nucleo.Tech.respository;

import com.Nucleo.Tech.modelo.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IcarritoRepository extends JpaRepository<Carrito, Long> {
}
