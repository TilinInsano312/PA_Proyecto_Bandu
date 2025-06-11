package com.banduu.usuario.mapper.impl;

import com.banduu.usuario.dto.abreviado.ClienteDTO;
import com.banduu.usuario.mapper.IConvertidorCliente;
import com.banduu.usuario.modelos.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ConvertidorCliente implements IConvertidorCliente {
    public Cliente DTOaEntidad(ClienteDTO dto) {
        return new Cliente(dto.nombre(), dto.apellido(), dto.edad(), dto.carrera(), dto.mismaCarrera(), dto.orientacion(), dto.genero(), dto.generosMusicales(), dto.canciones(), dto.artistas(), dto.albums());
    }
    public ClienteDTO entityToDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getNombre(), cliente.getApellido(), cliente.getEdad(), cliente.getCarrera(), cliente.isMismaCarrera(), cliente.getOrientacion(), cliente.getGenero(), cliente.getGenerosMusicales(), cliente.getCanciones(), cliente.getArtistas(), cliente.getAlbums());
    }
}
