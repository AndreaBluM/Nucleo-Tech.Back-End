package com.Nucleo.Tech.service;

import com.Nucleo.Tech.modelo.Rol;
import com.Nucleo.Tech.modelo.Usuario;
import com.Nucleo.Tech.respository.IrolRepository;
import com.Nucleo.Tech.respository.IusuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IusuarioRepository usuarioRepository;

    @Autowired
    private IrolRepository rolRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        // Asignar siempre el rol "customer" por defecto
        Rol rolCustomer = rolRepository.findAll().stream()
                .filter(r -> r.getTipo().equalsIgnoreCase("customer"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rol 'customer' no encontrado"));
        usuario.setRol(rolCustomer);
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getCorreo() != null &&
                        usuario.getCorreo().equals(email))
                .findFirst();
    }

    @Override
    @Transactional
    public Usuario autenticar(String correo, String contrasena) {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getCorreo() != null &&
                        usuario.getCorreo().equals(correo) &&
                        usuario.getContrasena().equals(contrasena))
                .findFirst()
                .orElse(null);
    }




}
