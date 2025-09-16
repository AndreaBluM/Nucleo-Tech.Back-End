package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Marca;
import com.Nucleo.Tech.respository.ImarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarcaServiceImpl implements IMarcaService {

    @Autowired
    private ImarcaRepository marcaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Marca> obtenerTodas() {
        return marcaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Marca> obtenerPorId(Long id) {
        return marcaRepository.findById(id);
    }

    @Override
    @Transactional
    public Marca guardar(Marca marca) {
        return marcaRepository.save(marca);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        marcaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return marcaRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Marca> buscarPorNombre(String nombre) {
        return marcaRepository.findAll().stream()
                .filter(marca -> marca.getNombre() != null &&
                        marca.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }
}
