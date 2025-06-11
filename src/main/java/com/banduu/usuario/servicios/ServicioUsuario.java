package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.abreviado.UsuarioDTO;
import com.banduu.usuario.mapper.IConvertidorUsuario;
import com.banduu.usuario.repositorios.UsuarioRepositorio;
import org.springframework.stereotype.Service;

    @Service
public class ServicioUsuario {

    private UsuarioRepositorio usuarioRepositorio;
    private IConvertidorUsuario convertidorUsuario;
    public ServicioUsuario(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }
    public void save(UsuarioDTO usuarioDTO) {
        usuarioRepositorio.insert(convertidorUsuario.DTOaEntidad(usuarioDTO));
    }

}
