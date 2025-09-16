package com.Nucleo.Tech.service.impl;

import com.Nucleo.Tech.modelo.DetalleCarrito;
import com.Nucleo.Tech.respository.IdetalleCarritoRepository;
import com.Nucleo.Tech.service.IDetalleCarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleCarritoServiceImpl implements IDetalleCarritoService {

    @Autowired
    private IdetalleCarritoRepository detalleCarritoRepository;

    @Override
    public List<DetalleCarrito> listarDetallesCarrito() {
        return detalleCarritoRepository.findAll();
    }

    @Override
    public Optional<DetalleCarrito> obtenerDetalleCarritoPorId(Long id) {
        return detalleCarritoRepository.findById(id);
    }

    @Override
    public DetalleCarrito guardarDetalleCarrito(DetalleCarrito detalleCarrito) {
        return detalleCarritoRepository.save(detalleCarrito);
    }

    @Override
    public void eliminarDetalleCarrito(Long id) {
        detalleCarritoRepository.deleteById(id);
    }

}
