package com.banduu.musica.servicio;

import com.banduu.musica.dto.AlbumDTO;
import com.banduu.musica.dto.ArtistaDTO;
import com.banduu.musica.dto.CancionDTO;
import com.banduu.musica.servicios.ServicioMusica;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ServicioMusicaTest {

    @Autowired
    private ServicioMusica servicioMusica;

    @Test
    void testBuscarMusicaPorCancion() {
        assertEquals(CancionDTO.class, servicioMusica.buscarCancion("Song 2").getClass());
    }
    @Test
    void testBuscarMusicaPorAlbum() {
        assertEquals(AlbumDTO.class, servicioMusica.buscarAlbum("Weezer").getClass());
    }
    @Test
    void testBuscarMusicaPorArtista() {
        assertEquals(ArtistaDTO.class, servicioMusica.buscarArtista("Luis Jara").getClass());
    }
}
