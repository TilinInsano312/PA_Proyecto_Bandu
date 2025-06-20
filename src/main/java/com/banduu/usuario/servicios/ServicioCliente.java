package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.ClienteDTO;
import com.banduu.usuario.modelos.Cliente;
import com.banduu.usuario.repositorios.ClienteRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioCliente {
    private final ClienteRepositorio clienteRepositorio;

    public ServicioCliente(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    public ClienteDTO save(ClienteDTO clienteDTO) {
       return entityToDTO(clienteRepositorio.insert(DTOaEntidad(clienteDTO)));

    }
    public List<ClienteDTO> findAll() {
        clienteRepositorio.findAll();
        return this.clienteRepositorio.findAll().stream()
                .map(cliente -> new ClienteDTO( cliente.getId(), cliente.getIdUsuario(), cliente.getNombre(), cliente.getApellido(), cliente.getEdad(), cliente.getCarrera(), cliente.getOrientacion(), cliente.getGenero(), cliente.getGenerosMusicales(), cliente.getCanciones(), cliente.getArtistas(), cliente.getAlbums()))
                .toList();
    }
    public ClienteDTO buscarPorId(String id) {
        return entityToDTO(this.clienteRepositorio.findById(id).orElse(null));
    }

    public void delete(String id) {
        clienteRepositorio.deleteById(id);
    }


    private Cliente DTOaEntidad(ClienteDTO dto) {
        return new Cliente(dto.id() ,dto.idUsuario(),dto.nombre(),  dto.apellido(), dto.edad(), dto.carrera(), dto.orientacion(), dto.genero(), dto.generosMusicales(), dto.canciones(), dto.artistas(), dto.albums());
    }
    private ClienteDTO entityToDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getIdUsuario(),cliente.getNombre(), cliente.getApellido(), cliente.getEdad(), cliente.getCarrera(), cliente.getOrientacion(), cliente.getGenero(), cliente.getGenerosMusicales(), cliente.getCanciones(), cliente.getArtistas(), cliente.getAlbums());
    }


}
