package com.banduu.usuario.servicios;

import com.banduu.musica.dto.AlbumDTO;
import com.banduu.musica.dto.ArtistaDTO;
import com.banduu.musica.dto.CancionDTO;
import com.banduu.musica.modelos.Album;
import com.banduu.musica.modelos.Artista;
import com.banduu.musica.modelos.Cancion;
import com.banduu.musica.servicios.ServicioMusica;
import com.banduu.usuario.dto.ClienteDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class ServicioEmparejamientoTest {
    @Autowired
    private ServicioEmparejamiento servicioEmparejamiento;
    @Autowired
    private ServicioCliente servicioCliente;
    @Autowired
    private  ServicioMusica servicioMusica;

    List<Cancion> c1 = List.of(
            new Cancion("1", "cancion1", "artista1", "png"),
            new Cancion("2", "cancion2", "artista2", "png")
    );
    List<Artista> c2 = List.of(
            new Artista("1", "artista1", "png"),
            new Artista("2", "artista2", "png")
    );
    List<Album> c3 = List.of(
            new Album("1", "album1", "artista1", "png"),
            new Album("2", "album2", "artista2", "png")
    );
    List<String> c4 = List.of(
            "rock", "pop", "jazz"
    );
    ClienteDTO cliente1 = new ClienteDTO("1", "cliente1", "", "", 10, "Ing. Informatica", "Heterosexual", "Masculino", c4, c1, c2, c3);
    ClienteDTO cliente2 = new ClienteDTO("2", "cliente2", "", "", 20, "Ing. Informatica", "Heterosexual", "Masculino", c4, c1, c2, c3);

    @BeforeEach
    public void limpiar() {
        servicioCliente.findAll().forEach(cliente -> servicioCliente.delete(cliente.id()));
    }
    @Test
    public void testEmparejarPorAlbum(){
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        assertEquals(0, servicioEmparejamiento.porAlbum("1").get("2"));
    }
    @Test
    public void testEmparejarPorArtista(){
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        assertEquals(6, servicioEmparejamiento.porArtista("1").get("2"));

    }
    @Test
    public void testEmparejarPorCancion(){
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        assertEquals(2, servicioEmparejamiento.porCancion("1").get("2"));

    }
    @Test
    public void testEmparejarPorCarrera(){
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        //Se espera un hashmap con las puntuaciones de cada usuario
        assertEquals( 1, servicioEmparejamiento.porCarrera("1").get("cliente2"));

    }
    @Test
    public void testEmparejarPorGenero(){
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        assertEquals(1, servicioEmparejamiento.porGenero("1").get("cliente2"));
    }
    @Test
    public void testEmparejarPorGenerosMusicales(){
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        assertEquals(3, servicioEmparejamiento.porGenerosMusicales("1").get("cliente2"));
    }
    @Test
    public void testDescartarMismaCarrera(){
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        List<String> resultado = servicioEmparejamiento.descartarMismaCarrera("1");
        assertEquals(2, resultado.size());
    }

}
