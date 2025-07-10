package com.banduu.usuario.controladores;

import com.banduu.usuario.dto.ClienteDTO;
import com.banduu.usuario.servicios.ServicioCliente;
import org.junit.jupiter.api.AfterEach;
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
class ControladorClienteTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ServicioCliente servicioCliente;

    @BeforeEach
    void setUp() {
        servicioCliente.findAll().forEach(cliente -> servicioCliente.delete(cliente.id()));
    }
    @AfterEach
    void tearDown() {
        servicioCliente.findAll().forEach(cliente -> servicioCliente.delete(cliente.id()));
    }
    @Test
    void testListarClientes() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/cliente")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testBuscarClientePorId() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",18,"","","","", List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/cliente/1")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testGuardarCliente() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/cliente")
                .header("Authorization", "no Auth")
                .contentType("application/json")
                .content("{\"nombreUsuario\": \"111111111\", \"contrasena\": \"test@example.com\", \"email\": \"test\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated());
    }
    @Test
    void testModificarNombreCliente() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",18,"","","","", List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/cliente/cliente1/nombre")
                .header("Authorization", "no Auth")
                .contentType("application/json")
                .content("{\"nombre\": \"ola\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testModificarApellidoCliente() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",18,"","","","", List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/cliente/cliente1/apellido")
                .header("Authorization", "no Auth")
                .contentType("application/json")
                .content("{\"apellido\": \"chao\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testModificarEdadCliente() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",18,"","","","", List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/cliente/cliente1/edad")
                .header("Authorization", "no Auth")
                .contentType("application/json")
                .content("20"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testModificarImagenCliente() throws Exception{
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",18,"","","","", List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/cliente/cliente1/imagen")
                .header("Authorization", "no Auth")
                .contentType("application/json")
                .content("{\"imagen\": \"img\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testModificarCarreraCliente() throws Exception{
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",18,"","","","", List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/cliente/cliente1/carrera")
                .header("Authorization", "no Auth")
                .contentType("application/json")
                .content("{\"carrera\": \"ing. informatica\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testModificarOrientacionCliente() throws Exception{
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",18,"","","","", List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/cliente/cliente1/orientacion")
                        .header("Authorization", "no Auth")
                        .contentType("application/json")
                        .content("{\"orientacion\": \"si\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testModificarGeneroCliente() throws Exception{
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",18,"","","","", List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/cliente/cliente1/genero")
                        .header("Authorization", "no Auth")
                        .contentType("application/json")
                        .content("{\"genero\": \"so\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testModificarGeneroMusicalesCliente() throws Exception{
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",18,"","","","", List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/cliente/cliente1/generosMusicales")
                        .header("Authorization", "no Auth")
                        .contentType("application/json")
                        .content("[\"rock\", \"pop\"]"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testModificarCancionesCliente() throws Exception{
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",18,"","","","", List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/cliente/cliente1/canciones")
                        .header("Authorization", "no Auth")
                        .contentType("application/json")
                        .content("[{\"nombreCancion\": \"cancion1\", \"artistaCancion\": \"Cancion 1\", \"generoCancion\": \"genero\", \"imagenCancion\": \"img\"}]"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testModificarArtistasCliente() throws Exception{
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",18,"","","","", List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/cliente/cliente1/artistas")
                        .header("Authorization", "no Auth")
                        .contentType("application/json")
                        .content("[{\"nombreArtista\": \"artista1\", \"generoArtista\": \"Artista 1\", \"imagenArtista\": \"img\"}]"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testModificarAlbumesCliente() throws Exception{
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",18,"","","","", List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/cliente/cliente1/albums")
                        .header("Authorization", "no Auth")
                        .contentType("application/json")
                        .content("[{\"nombreAlbum\": \"album1\", \"artistaAlbum\": \"Album 1\", \"generoAlbum\": \"genero\", \"imagenAlbum\": \"img\"}]"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
}
