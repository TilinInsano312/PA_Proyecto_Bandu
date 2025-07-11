package com.banduu.usuario.controladores;

import com.banduu.usuario.servicios.ServicioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ControladorUsuarioTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ServicioUsuario servicioUsuario;

    @BeforeEach
    void setUp() {
        servicioUsuario.findAll().forEach(usuario -> servicioUsuario.delete(usuario.id()));
    }
    @Test
    void testListarUsuarios() throws Exception {
        servicioUsuario.save( new com.banduu.usuario.dto.UsuarioDTO("1", "user1", "pass1", "a"));
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/usuario").header("Authorization", "no Auth" ))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testBuscarUsuarioPorId() throws Exception {
        servicioUsuario.save( new com.banduu.usuario.dto.UsuarioDTO("1", "user1", "pass1", "a"));
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/usuario/1").header("Authorization", "no Auth" ))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testGuardarUsuario() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/usuario").header("Authorization", "no Auth" )
                .contentType("application/json")
                .content("{\"nombreUsuario\": \"111111111\", \"contrasena\": \"test@example.com\", \"email\": \"test\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated());
    }
    @Test
    void testModificarContrasena() throws Exception {
        servicioUsuario.save( new com.banduu.usuario.dto.UsuarioDTO("1", "user1", "pass1", "a"));
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/usuario/1/contrasena").header("Authorization", "no Auth" )
                .contentType("application/json")
                .content("{\"contrasena\": \"newPassword\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testModificarEmail() throws Exception {
        servicioUsuario.save( new com.banduu.usuario.dto.UsuarioDTO("1", "user1", "pass1", "a"));
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/usuario/1/email").header("Authorization", "no Auth" )
                .contentType("application/json")
                .content("{\"email\": \"email@example.com\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
}
