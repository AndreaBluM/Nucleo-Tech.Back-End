package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Rol;
import java.util.List;
import java.util.Optional;

public interface IRolService {
    List<Rol> listarRoles();
    Optional<Rol> obtenerRolPorId(Long id);
    Rol guardarRol(Rol rol);
    void eliminarRol(Long id);
}
