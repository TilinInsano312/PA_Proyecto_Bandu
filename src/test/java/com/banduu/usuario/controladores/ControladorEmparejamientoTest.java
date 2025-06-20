package com.banduu.usuario.controladores;

import com.banduu.musica.modelos.Album;
import com.banduu.musica.modelos.Artista;
import com.banduu.musica.modelos.Cancion;
import com.banduu.usuario.dto.ClienteDTO;
import com.banduu.usuario.servicios.ServicioCliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ControladorEmparejamientoTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ServicioCliente servicioCliente;

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
    void limpiar() {
        servicioCliente.findAll().forEach(cliente -> servicioCliente.delete(cliente.id()));
    }

    @Test
    void testEmparejarPorAlbum() throws Exception {
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/emparejamiento/album/1")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testEmparejarPorArtista() throws Exception {
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/emparejamiento/artista/1")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testEmparejarPorCancion() throws Exception {
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/emparejamiento/cancion/1")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testEmparejarPorCarrera() throws Exception {
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/emparejamiento/carrera/1")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testEmparejarPorGenero() throws Exception {
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/emparejamiento/genero/1")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testEmparejarPorOrientacion() throws Exception {
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/emparejamiento/orientacion/1")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testDescartarMismaCarrera() throws Exception {
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/emparejamiento/descartar/1")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testEmparejarPorGenerosMusicales() throws Exception {
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/emparejamiento/generosmusicales/1")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
}
