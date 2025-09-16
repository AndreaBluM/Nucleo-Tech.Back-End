package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Rol;
import com.Nucleo.Tech.respository.IrolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    private IrolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> obtenerPorId(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    @Transactional
    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return rolRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> buscarPorNombre(String nombre) {
        return rolRepository.findAll().stream()
                .filter(rol -> rol.getTipo() != null &&
                        rol.getTipo().equalsIgnoreCase(nombre))
                .findFirst();
    }
}
