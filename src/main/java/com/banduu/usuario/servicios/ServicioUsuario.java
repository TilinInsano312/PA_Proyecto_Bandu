package com.banduu.usuario.servicios;

import com.banduu.usuario.modelos.Usuario;
import com.banduu.usuario.repositorios.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioUsuario {

    private final UsuarioRepositorio usuarioRepositorio;

    public ServicioUsuario (UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public Usuario getUsuarioById(String id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    public List<Usuario> getAllByNombre(String nombreUsuario) {
       return usuarioRepositorio.findAllByNombreUsuario(nombreUsuario);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public Usuario updateContrasena(String id, String nuevaContrasena) {
        return usuarioRepositorio.updateContrasena(id, nuevaContrasena);
    }

}
