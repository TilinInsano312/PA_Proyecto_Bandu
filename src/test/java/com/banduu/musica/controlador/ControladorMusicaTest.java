package com.banduu.musica.controlador;

import com.banduu.musica.servicios.ServicioMusica;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ControladorMusicaTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ServicioMusica servicioMusica;

    @Test
    void testBuscarMusicaPorCancion() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/musica/cancion/Song 2")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.nombreCancion").value("Song 2"));
    }
    @Test
    void testBuscarMusicaPorAlbum() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/musica/album/Weezer")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.nombreAlbum").value("Weezer"));
    }
    @Test
    void testBuscarMusicaPorArtista() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/musica/artista/Luis Jara")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.nombreArtista").value("Luis Jara"));
    }
}
