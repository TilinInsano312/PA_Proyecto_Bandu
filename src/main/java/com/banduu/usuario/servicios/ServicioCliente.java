package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.abreviado.ClienteDTO;
import com.banduu.usuario.mapper.IConvertidorCliente;
import com.banduu.usuario.repositorios.ClienteRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioCliente {
    private final ClienteRepositorio clienteRepositorio;
    private IConvertidorCliente convertidorCliente;

    public ServicioCliente(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    public void save(ClienteDTO clienteDTO) {
        clienteRepositorio.insert(convertidorCliente.DTOaEntidad(clienteDTO));

    }
    public List<ClienteDTO> findAll() {
        clienteRepositorio.findAll();
        return this.clienteRepositorio.findAll().stream()
                .map(cliente -> new ClienteDTO( cliente.getId(), cliente.getNombre(), cliente.getApellido(), cliente.getEdad(), cliente.getCarrera(), cliente.isMismaCarrera(), cliente.getOrientacion(), cliente.getGenero(), cliente.getGenerosMusicales(), cliente.getCanciones(), cliente.getArtistas(), cliente.getAlbums()))
                .toList();
    }

}
