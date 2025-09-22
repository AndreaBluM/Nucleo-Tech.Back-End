package com.Nucleo.Tech.respository;

import com.Nucleo.Tech.modelo.Rol;
import com.Nucleo.Tech.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IrolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByTipo(String tipo);
}
