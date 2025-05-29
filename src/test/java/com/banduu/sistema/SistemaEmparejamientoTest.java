package com.banduu.sistema;
import com.banduu.musica.modelos.Album;
import com.banduu.musica.modelos.Artista;
import com.banduu.musica.modelos.Cancion;
import com.banduu.usuario.servicios.SistemaEmparejamiento;
import com.banduu.usuario.modelos.Cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SistemaEmparejamientoTest {
    SistemaEmparejamiento sistemaEmparejamiento = new SistemaEmparejamiento();
    Cliente perfilUsuario = new Cliente("usuario1", "contrasena1", "email1", "nombre1", "apellido1", 20, "orientacion1", "carrera", true, "genero1");
    Cliente perfilUsuario2 = new Cliente("usuario2", "contrasena2", "email2", "nombre2", "apellido2", 25, "orientacion2", "carrera", true, "genero2");
    Cancion cancion = new Cancion("Despacito", "Luis Fonsi", "latin", "https://i.scdn.co/image/ab67616d0000b273ef0d4234e1a645740f77d59c");
    Album album = new Album("OK Computer", "Radiohead", "art rock", "https://i.scdn.co/image/ab67616d0000b273c8b444df094279e70d0ed856");
    Artista artista = new Artista("Michael Jackson", "null", "https://i.scdn.co/image/ab6761610000e5eb997cc9a4aec335d46c9481fd");
    List<Cliente> listaClientes = new ArrayList<>();
    @BeforeEach
    void setUp() {
        perfilUsuario.agregarAlbum(album);
        perfilUsuario.agregarArtistas(artista);
        perfilUsuario.agregarCanciones(cancion);
        perfilUsuario.agregarGenerosMusicales("rock");
        perfilUsuario2.agregarAlbum(album);
        perfilUsuario2.agregarArtistas(artista);
        perfilUsuario2.agregarCanciones(cancion);
        perfilUsuario2.agregarGenerosMusicales("rock");
        listaClientes.add(perfilUsuario);
        listaClientes.add(perfilUsuario2);
        GestorArchivos.escribirListaObjetos(listaClientes, Cliente.class);
    }

    @Test
    void referencia() {
        assertEquals(perfilUsuario, sistemaEmparejamiento.referencia("usuario1").get(0));
    }

    @Test
    void general() {
        assertDoesNotThrow(() -> sistemaEmparejamiento.general("usuario1"));
    }

    @Test
    void porAlbum() {
        assertEquals(2,sistemaEmparejamiento.porAlbum("usuario1").get("usuario2"));
    }

    @Test
    void porArtista() {
        assertEquals(3,sistemaEmparejamiento.porArtista("usuario1").get("usuario2"));
    }

    @Test
    void porCancion() {
        assertEquals(1,sistemaEmparejamiento.porCancion("usuario1").get("usuario2"));
    }

    @Test
    void porCarrera() {
        assertEquals(1,sistemaEmparejamiento.porCarrera("usuario1").get("usuario2"));
    }

    @Test
    void porOrientacion() {
        assertNotEquals(1,sistemaEmparejamiento.porOrientacion("usuario1").get("usuario2"));
    }

    @Test
    void porGenero() {
        assertNotEquals(1,sistemaEmparejamiento.porGenero("usuario1").get("usuario2"));
    }

    @Test
    void porGenerosMusicales() {
        assertEquals(1,sistemaEmparejamiento.porGenerosMusicales("usuario1").get("usuario2"));
    }

    @Test
    void descartarMismaCarrera() {
        assertEquals("usuario2",sistemaEmparejamiento.descartarMismaCarrera("usuario1").get(0));
    }
}