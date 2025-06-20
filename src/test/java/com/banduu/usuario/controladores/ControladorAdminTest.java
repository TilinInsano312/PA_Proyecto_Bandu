package com.banduu.usuario.controladores;

import com.banduu.usuario.servicios.ServicioAdmin;
import org.junit.jupiter.api.AfterEach;
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
class ControladorAdminTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ServicioAdmin servicioAdmin;
    @BeforeEach
    void setUp() {
        servicioAdmin.findAll().forEach(admin -> servicioAdmin.delete(admin.id()));
    }
    @AfterEach
    void tearDown() {
        servicioAdmin.findAll().forEach(admin -> servicioAdmin.delete(admin.id()));
    }
    @Test
    void testListarAdmins() throws Exception {
        servicioAdmin.save(new com.banduu.usuario.dto.AdminDTO("1", "admin1", "pass1"));
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/admin")
                .header("Authorization", "no Auth"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testGuardarAdmin() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/admin")
                .header("Authorization", "no Auth")
                .contentType("application/json")
                .content("{\"nombreUsuario\": \"111111111\", \"contrasena\": \"test@example.com\", \"email\": \"test\"}"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated());
    }

}
