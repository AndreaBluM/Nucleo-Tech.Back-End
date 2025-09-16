package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.DetalleCarrito;
import com.Nucleo.Tech.respository.IdetalleCarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetalleCarritoServiceImpl implements IDetalleCarritoService {

    @Autowired
    private IdetalleCarritoRepository detalleCarritoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DetalleCarrito> obtenerTodos() {
        return detalleCarritoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleCarrito> obtenerPorId(Long id) {
        return detalleCarritoRepository.findById(id);
    }

    @Override
    @Transactional
    public DetalleCarrito guardar(DetalleCarrito detalleCarrito) {
        return detalleCarritoRepository.save(detalleCarrito);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        detalleCarritoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return detalleCarritoRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleCarrito> obtenerPorCarritoId(Long carritoId) {
        return detalleCarritoRepository.findAll().stream()
                .filter(detalle -> detalle.getCarrito() != null &&
                        detalle.getCarrito().getId().equals(carritoId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleCarrito> buscarDetalleCarritoPorProductoYCarrito(Long productoId, Long carritoId) {
        return detalleCarritoRepository.findAll().stream()
                .filter(detalle -> detalle.getCarrito() != null &&
                        detalle.getCarrito().getId().equals(carritoId) &&
                        detalle.getProducto() != null &&
                        detalle.getProducto().getId().equals(productoId))
                .findFirst();
    }
}
