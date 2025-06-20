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
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","", List.of(),List.of(),List.of(),List.of());
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
}
