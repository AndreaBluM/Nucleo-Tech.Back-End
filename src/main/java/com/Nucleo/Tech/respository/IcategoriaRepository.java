package com.Nucleo.Tech.respository;

import com.Nucleo.Tech.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IcategoriaRepository extends JpaRepository<Categoria, Long> {
}
