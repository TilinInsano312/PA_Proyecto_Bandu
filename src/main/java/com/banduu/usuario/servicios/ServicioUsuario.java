package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.abreviado.UsuarioDTO;
import com.banduu.usuario.modelos.Usuario;
import com.banduu.usuario.repositorios.UsuarioRepositorio;
import org.springframework.stereotype.Service;


    @Service
public class ServicioUsuario {

    private UsuarioRepositorio usuarioRepositorio;
    public ServicioUsuario(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }
    public void save(UsuarioDTO usuarioDTO) {
        usuarioRepositorio.insert(dtoToEntity(usuarioDTO));
    }
    public Usuario dtoToEntity(UsuarioDTO dto){
        return new Usuario(dto.id(), dto.nombreUsuario(), dto.contrasena(), dto.email());
    }
}
