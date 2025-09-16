package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Carrito;
import com.Nucleo.Tech.respository.IcarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoServiceImpl implements ICarritoService {

    @Autowired
    private IcarritoRepository carritoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Carrito> obtenerTodos() {
        return carritoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Carrito> obtenerPorId(Long id) {
        return carritoRepository.findById(id);
    }

    @Override
    @Transactional
    public Carrito guardar(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        carritoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return carritoRepository.existsById(id);
    }

    @Override
    public Optional<Carrito> buscarPorUsuarioId(Long usuarioId) {
        return carritoRepository.findById(usuarioId);
    }


}
