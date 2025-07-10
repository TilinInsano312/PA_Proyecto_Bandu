package com.banduu.usuario.servicios;

import com.banduu.musica.dto.AlbumDTO;
import com.banduu.musica.dto.ArtistaDTO;
import com.banduu.musica.dto.CancionDTO;
import com.banduu.musica.modelos.Album;
import com.banduu.musica.modelos.Artista;
import com.banduu.musica.modelos.Cancion;
import com.banduu.usuario.dto.ClienteDTO;
import com.banduu.usuario.modelos.Cliente;
import com.banduu.usuario.repositorios.ClienteRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Servicio para gestionar las operaciones relacionadas con los clientes.
 * Proporciona metodos para guardar, listar, buscar y eliminar clientes,
 * así como para modificar sus atributos.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
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
                .map(cliente -> new ClienteDTO( cliente.getId(), cliente.getIdUsuario(), cliente.getNombre(), cliente.getApellido(), cliente.getEdad(),cliente.getImagen(), cliente.getCarrera(), cliente.getOrientacion(), cliente.getGenero(), cliente.getGenerosMusicales(), cliente.getCanciones(), cliente.getArtistas(), cliente.getAlbums()))
                .toList();
    }
    public ClienteDTO buscarPorId(String id) {
        return entityToDTO(this.clienteRepositorio.findById(id).orElse(null));
    }
    public ClienteDTO buscarPorIdUsuario(String idUsuario) {
        return entityToDTO(this.clienteRepositorio.findByIdUsuario(idUsuario).orElse(null));
    }

    public void delete(String id) {
        clienteRepositorio.deleteById(id);
    }


    private Cliente DTOaEntidad(ClienteDTO dto) {
        return new Cliente(dto.id() ,dto.idUsuario(),dto.nombre(),  dto.apellido(), dto.edad(),dto.imagen(), dto.carrera(), dto.orientacion(), dto.genero(), dto.generosMusicales(), dto.canciones(), dto.artistas(), dto.albums());
    }
    private ClienteDTO entityToDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getIdUsuario(),cliente.getNombre(), cliente.getApellido(), cliente.getEdad(),cliente.getImagen(), cliente.getCarrera(), cliente.getOrientacion(), cliente.getGenero(), cliente.getGenerosMusicales(), cliente.getCanciones(), cliente.getArtistas(), cliente.getAlbums());
    }
    private Cancion DTOtoCancion(CancionDTO cancion) {
        return new Cancion(cancion.nombreCancion(), cancion.artistaCancion(), cancion.generoCancion(), cancion.imagenCancion());
    }
    private Album DTOtoAlbum(AlbumDTO album) {
        return new Album(album.nombreAlbum(), album.artistaAlbum(), album.generoAlbum(), album.imagenAlbum());
    }
    private Artista DTOtoArtista(ArtistaDTO artista) {
        return new Artista(artista.nombreArtista(), artista.generoArtista(), artista.imagenArtista());
    }

    public void modificarNombre(String idUsuario, String nombre ) {
        clienteRepositorio.updateNombreByIdUsuario(idUsuario, nombre);
    }
    public void modificarApellido(String idUsuario, String apellido) {
        clienteRepositorio.updateApellidoByIdUsuario(idUsuario, apellido);
    }
    public void modificarEdad(String idUsuario, int edad) {
        clienteRepositorio.updateEdadByIdUsuario(idUsuario, edad);
    }
    public void modificarImagen(String idUsuario, String imagen) {
        clienteRepositorio.updateImagenByIdUsuario(idUsuario, imagen);
    }
    public void modificarCarrera(String idUsuario, String carrera) {
        clienteRepositorio.updateCarreraByIdUsuario(idUsuario, carrera);
    }
    public void modificarOrientacion(String idUsuario, String orientacion) {
        clienteRepositorio.updateOrientacionByIdUsuario(idUsuario, orientacion);
    }
    public void modificarGenero(String idUsuario, String genero) {
        clienteRepositorio.updateGeneroByIdUsuario(idUsuario, genero);
    }
    public void modificarGenerosMusicales(String idUsuario, List<String> generosMusicales) {
        clienteRepositorio.updateGeneroMusicalesByIdUsuario(idUsuario, generosMusicales);
    }
    public void modificarCanciones(String idUsuario, List<CancionDTO> canciones) {
        List<Cancion> cancionesEntidades = canciones.stream()
                .map(this::DTOtoCancion)
                .toList();
        clienteRepositorio.updateCancionesByIdUsuario(idUsuario, cancionesEntidades);
    }
    public void modificarArtistas(String idUsuario, List<ArtistaDTO> artistas) {
        List<Artista> artistasEntidades = artistas.stream()
                .map(this::DTOtoArtista)
                .toList();
        clienteRepositorio.updateArtistasByIdUsuario(idUsuario, artistasEntidades);
    }
    public void modificarAlbums(String idUsuario, List<AlbumDTO> albums) {
        List<Album> albumsEntidades = albums.stream()
                .map(this::DTOtoAlbum)
                .toList();
        clienteRepositorio.updateAlbumByIdUsuario(idUsuario, albumsEntidades);
    }
}
