package sistema;

import modelos.musica.Album;
import modelos.musica.Artista;
import modelos.musica.Cancion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioMusicaTest {
    ServicioMusica servicioMusica = new ServicioMusica();
    Cancion cancion = new Cancion("Despacito", "Luis Fonsi", "latin", "https://i.scdn.co/image/ab67616d0000b273ef0d4234e1a645740f77d59c");
    Album album = new Album("OK Computer", "Radiohead", "art rock", "https://i.scdn.co/image/ab67616d0000b273c8b444df094279e70d0ed856");
    Artista artista = new Artista("Michael Jackson", "null", "https://i.scdn.co/image/ab6761610000e5eb997cc9a4aec335d46c9481fd");

    @Test
    void respuestaHTTP() {
        assertEquals(200, servicioMusica.respuestaHTTP("https://api.spotify.com/v1/search?q=despacito&type=track").statusCode());
    }

    @Test
    void buscarAlbum() {
        assertEquals(album, servicioMusica.buscarAlbum("Ok computer"));
    }

    @Test
    void buscarArtista() {
        assertEquals(artista, servicioMusica.buscarArtista("Michael Jackson"));
    }

    @Test
    void buscarCancion() {
        assertEquals(cancion, servicioMusica.buscarCancion("Despacito"));
    }
}