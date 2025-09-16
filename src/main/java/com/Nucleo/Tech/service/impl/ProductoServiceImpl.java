package com.Nucleo.Tech.service.impl;

import com.Nucleo.Tech.modelo.Producto;
import com.Nucleo.Tech.respository.IproductoRepository;
import com.Nucleo.Tech.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private IproductoRepository productoRepository;

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }


    @Override
    public List<Producto> buscarPorMarca(Long marcaId) {
        return productoRepository.findByMarcaId(marcaId);
    }
}
