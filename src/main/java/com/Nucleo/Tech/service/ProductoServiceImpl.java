package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Producto;
import com.Nucleo.Tech.respository.IproductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private IproductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    @Transactional
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return productoRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerPorCategoria(Long categoriaId) {
        return productoRepository.findAll().stream()
                .filter(producto -> producto.getCategoria() != null &&
                        producto.getCategoria().getId().equals(categoriaId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerPorMarca(Long marcaId) {
        return productoRepository.findAll().stream()
                .filter(producto -> producto.getMarca() != null &&
                        producto.getMarca().getId().equals(marcaId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findAll().stream()
                .filter(producto -> producto.getNombre() != null &&
                        producto.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }
}
