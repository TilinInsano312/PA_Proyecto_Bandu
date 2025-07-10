package com.banduu.usuario.servicios;

import com.banduu.musica.dto.AlbumDTO;
import com.banduu.musica.dto.ArtistaDTO;
import com.banduu.musica.dto.CancionDTO;
import com.banduu.musica.modelos.Album;
import com.banduu.musica.modelos.Artista;
import com.banduu.musica.modelos.Cancion;
import com.banduu.usuario.dto.ClienteDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ServicioClienteTest {

    @Autowired
    private ServicioUsuario servicioUsuario;
    @Autowired
    private ServicioCliente servicioCliente;

    @BeforeEach
    void limpiar() {
        servicioCliente.findAll().forEach(cliente -> servicioCliente.delete(cliente.id()));
    }
    @AfterEach
    void limpiarDatos() {
        servicioCliente.findAll().forEach(cliente -> servicioCliente.delete(cliente.id()));
        servicioUsuario.findAll().forEach(usuario -> servicioUsuario.delete(usuario.id()));
    }
    @Test
    void testCrearCliente() {
        ClienteDTO clienteDTO = new ClienteDTO("20", "a","","",10,"","","","", List.of(),List.of(),List.of(),List.of());
        assertEquals(clienteDTO, servicioCliente.save(clienteDTO));
    }
    @Test
    void testListarClientes() {
        ClienteDTO cliente1 = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        ClienteDTO cliente2 = new ClienteDTO("2", "cliente2","","",20,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        assertEquals(2, servicioCliente.findAll().size());
    }
    @Test
    void testBuscarClientePorId() {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        assertEquals(clienteDTO, servicioCliente.buscarPorId("1"));
    }
    @Test
    void testBuscarIdUsuario(){
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        assertEquals(1, servicioCliente.buscarPorIdUsuario("cliente1").id());
    }
    @Test
    void testEliminarCliente() {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        assertEquals(clienteDTO, servicioCliente.buscarPorId("1"));
        servicioCliente.delete("1");
        assertThrows(Exception.class, () -> servicioCliente.buscarPorId("1"));
    }
    @Test
    void testModificarNombreUsuario() {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        servicioCliente.modificarNombre("cliente1", "nuevoNombre");
        assertEquals("nuevoNombre", servicioCliente.buscarPorId("1").nombre());
    }
    @Test
    void testModificarApellido() {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        servicioCliente.modificarApellido("cliente1", "nuevoApellido");
        assertEquals("nuevoApellido", servicioCliente.buscarPorId("1").apellido());
    }
    @Test
    void testModificarEdad(){
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        servicioCliente.modificarEdad("cliente1", 20);
        assertEquals(20, servicioCliente.buscarPorId("1").edad());
    }
    @Test
    void testModificarImagen(){
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        servicioCliente.modificarImagen("cliente1", "nuevaImagen.jpg");
        assertEquals("nuevaImagen.jpg", servicioCliente.buscarPorId("1").imagen());
    }
    @Test
    void testModificarCarrera() {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        servicioCliente.modificarCarrera("cliente1", "Ingenieria Informatica");
        assertEquals("Ingenieria Informatica", servicioCliente.buscarPorId("1").carrera());
    }
    @Test
    void testModificarOrientacion() {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        servicioCliente.modificarOrientacion("cliente1", "Heterosexual");
        assertEquals("Heterosexual", servicioCliente.buscarPorId("1").orientacion());
    }
    @Test
    void testModificarGenero(){
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        servicioCliente.modificarGenero("cliente1", "Masculino");
        assertEquals("Masculino", servicioCliente.buscarPorId("1").genero());
    }
    @Test
    void testModificarGenerosMusicales() {
        ClienteDTO clienteDTO = new ClienteDTO("1", "1","","",10,"","","","",List.of("so"),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        List<String> generosMusicales = List.of("Rock", "Pop");
        servicioCliente.modificarGenerosMusicales("1", generosMusicales);
        assertEquals(generosMusicales, servicioCliente.buscarPorId("1").generosMusicales());
    }
    @Test
    void testModificarCanciones() {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        CancionDTO cancion1 = new CancionDTO("Cancion1", "artista", "genero", "img");
        List<CancionDTO> canciones = List.of(cancion1);
        Cancion cancion = new Cancion(cancion1.nombreCancion(), cancion1.artistaCancion(), cancion1.generoCancion(), cancion1.imagenCancion());
        List<Cancion> cancionesEsperadas = List.of(cancion);
        servicioCliente.modificarCanciones("cliente1", canciones);
        assertEquals(cancionesEsperadas, servicioCliente.buscarPorId("1").canciones());
    }
    @Test
    void testModificarArtistas() {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        ArtistaDTO artista1 = new ArtistaDTO("Artista1", "genero", "img");
        List<ArtistaDTO> artistas = List.of(artista1);
        Artista artista = new Artista(artista1.nombreArtista(), artista1.generoArtista(), artista1.imagenArtista());
        List<Artista> artistasEsperados = List.of(artista);
        servicioCliente.modificarArtistas("cliente1", artistas);
        assertEquals(artistasEsperados, servicioCliente.buscarPorId("1").artistas());
    }
    @Test
    void testModificarAlbumes(){
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        AlbumDTO album1 = new AlbumDTO("Album1", "Artista1", "genero", "img");
        List<AlbumDTO> albums = List.of(album1);
        Album album = new Album(album1.nombreAlbum(), album1.artistaAlbum(), album1.generoAlbum(), album1.imagenAlbum());
        List<Album> albumesEsperados = List.of(album);
        servicioCliente.modificarAlbums("cliente1", albums);
        assertEquals(albumesEsperados, servicioCliente.buscarPorId("1").albums());
    }

}
