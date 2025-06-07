package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.abreviado.ClienteDTO;
import com.banduu.usuario.modelos.Cliente;
import com.banduu.usuario.repositorios.ClienteRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioCliente {
    private ClienteRepositorio clienteRepositorio;
    public ServicioCliente(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    public void save(ClienteDTO clienteDTO) {
        clienteRepositorio.insert(dtoToEntity(clienteDTO));

    }
    public List<ClienteDTO> findAll() {
        clienteRepositorio.findAll();
        return this.clienteRepositorio.findAll().stream()
                .map(cliente -> new ClienteDTO( cliente.getId(), cliente.getNombre(), cliente.getApellido(), cliente.getEdad(), cliente.getCarrera(), cliente.isMismaCarrera(), cliente.getOrientacion(), cliente.getGenero(), cliente.getGenerosMusicales(), cliente.getCanciones(), cliente.getArtistas(), cliente.getAlbums()))
                .toList();
    }
    public Cliente dtoToEntity( ClienteDTO dto) {
        return new Cliente(dto.nombre(), dto.apellido(), dto.edad(), dto.carrera(), dto.mismaCarrera(), dto.orientacion(), dto.genero(), dto.generosMusicales(), dto.canciones(), dto.artistas(), dto.albums());
    }
    public ClienteDTO entityToDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getNombre(), cliente.getApellido(), cliente.getEdad(), cliente.getCarrera(), cliente.isMismaCarrera(), cliente.getOrientacion(), cliente.getGenero(), cliente.getGenerosMusicales(), cliente.getCanciones(), cliente.getArtistas(), cliente.getAlbums());
    }
}
