package com.banduu.usuario.mapper;

import com.banduu.usuario.dto.abreviado.ClienteDTO;
import com.banduu.usuario.modelos.Cliente;

public interface IConvertidorCliente {
    Cliente DTOaEntidad(ClienteDTO dto);
    ClienteDTO entityToDTO(Cliente cliente);
}
