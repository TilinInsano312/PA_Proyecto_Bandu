package com.banduu.usuario.mapper.impl;

import com.banduu.usuario.dto.abreviado.UsuarioDTO;
import com.banduu.usuario.mapper.IConvertidorUsuario;
import com.banduu.usuario.modelos.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ConvertidorUsuario implements IConvertidorUsuario {
    public Usuario DTOaEntidad(UsuarioDTO dto){
        return new Usuario(dto.id(), dto.nombreUsuario(), dto.contrasena(), dto.email());
    }
}
