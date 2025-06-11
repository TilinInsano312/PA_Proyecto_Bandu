package com.banduu.usuario.mapper;

import com.banduu.usuario.dto.abreviado.UsuarioDTO;
import com.banduu.usuario.modelos.Usuario;

public interface IConvertidorUsuario {
    Usuario DTOaEntidad(UsuarioDTO dto);
}
