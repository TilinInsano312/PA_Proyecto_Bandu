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

    public void modificarNombre(String nombre, String idUsuario) {
        clienteRepositorio.updateNombreByIdUsuario(nombre, idUsuario);
    }
    public void modificarApellido(String apellido, String idUsuario) {
        clienteRepositorio.updateApellidoByIdUsuario(apellido, idUsuario);
    }
    public void modificarEdad(int edad, String idUsuario) {
        clienteRepositorio.updateEdadByIdUsuario(edad, idUsuario);
    }
    public void modificarImagen(String imagen, String idUsuario) {
        clienteRepositorio.updateImagenByIdUsuario(imagen, idUsuario);
    }
    public void modificarCarrera(String carrera, String idUsuario) {
        clienteRepositorio.updateCarreraByIdUsuario(carrera, idUsuario);
    }
    public void modificarOrientacion(String orientacion, String idUsuario) {
        clienteRepositorio.updateOrientacionByIdUsuario(orientacion, idUsuario);
    }
    public void modificarGenero(String genero, String idUsuario) {
        clienteRepositorio.updateGeneroByIdUsuario(genero, idUsuario);
    }
    public void modificarGenerosMusicales(List<String> generosMusicales, String idUsuario) {
        clienteRepositorio.updateGeneroMusicalesByIdUsuario(generosMusicales, idUsuario);
    }
    public void modificarCanciones(List<CancionDTO> canciones, String idUsuario) {
        List<Cancion> cancionesEntidades = canciones.stream()
                .map(this::DTOtoCancion)
                .toList();
        clienteRepositorio.updateCancionesByIdUsuario(cancionesEntidades, idUsuario);
    }
    public void modificarArtistas(List<ArtistaDTO> artistas, String idUsuario) {
        List<Artista> artistasEntidades = artistas.stream()
                .map(this::DTOtoArtista)
                .toList();
        clienteRepositorio.updateArtistasByIdUsuario(artistasEntidades, idUsuario);
    }
    public void modificarAlbums(List<AlbumDTO> albums, String idUsuario) {
        List<Album> albumsEntidades = albums.stream()
                .map(this::DTOtoAlbum)
                .toList();
        clienteRepositorio.updateAlbumByIdUsuario(albumsEntidades, idUsuario);
    }
}
