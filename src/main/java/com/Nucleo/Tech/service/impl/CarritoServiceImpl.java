package com.Nucleo.Tech.service.impl;

import com.Nucleo.Tech.modelo.Carrito;
import com.Nucleo.Tech.respository.IcarritoRepository;
import com.Nucleo.Tech.service.ICarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoServiceImpl implements ICarritoService {

    @Autowired
    private IcarritoRepository carritoRepository;

    @Override
    public List<Carrito> listarCarritos() {
        return carritoRepository.findAll();
    }

    @Override
    public Optional<Carrito> obtenerCarritoPorId(Long id) {
        return carritoRepository.findById(id);
    }

    @Override
    public Carrito guardarCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    @Override
    public void eliminarCarrito(Long id) {
        carritoRepository.deleteById(id);
    }

    @Override
    public Optional<Carrito> obtenerCarritoDeUsuario(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId);
    }
}
