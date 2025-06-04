package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.UsuarioDTO;
import com.banduu.usuario.repositorios.UsuarioRepositorio;
import org.springframework.stereotype.Service;


@Service
public class ServicioUsuario {

    private UsuarioRepositorio usuarioRepositorio;
    public ServicioUsuario(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }
    public void save(UsuarioDTO usuarioDTO) {
        usuarioRepositorio.insert(usuarioDTO);

    }
}
