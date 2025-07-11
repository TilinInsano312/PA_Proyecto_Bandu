package com.banduu.conversacion.controladores;

import com.banduu.conversacion.modelos.Mensaje;
import com.banduu.conversacion.servicios.ServicioMensaje;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ControladorMensajesTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ServicioMensaje servicioMensaje;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private com.banduu.conversacion.repositorios.RepositorioConversacion repositorioConversacion;
    
    @Autowired
    private com.banduu.conversacion.repositorios.RepositorioMensaje repositorioMensaje;

    @BeforeEach
    void setUp() {
        repositorioMensaje.deleteAll();
        repositorioConversacion.deleteAll();
    }
    
    @AfterEach
    void tearDown() {
        repositorioMensaje.deleteAll();
        repositorioConversacion.deleteAll();
    }

    @Test
    void testObtenerMensajesConversacionExistente() throws Exception {
        Mensaje mensaje1 = Mensaje.builder()
                .idRemitente("usuario1")
                .idDestinatario("usuario2")
                .contenido("Hola usuario2")
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        Mensaje mensaje2 = Mensaje.builder()
                .idRemitente("usuario2")
                .idDestinatario("usuario1")
                .contenido("Hola usuario1")
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        servicioMensaje.save(mensaje1);
        servicioMensaje.save(mensaje2);

        mockMvc.perform(get("/api/mensajes/usuario1/usuario2")
                .header("Authorization", "no Auth"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idRemitente").exists())
                .andExpect(jsonPath("$[0].idDestinatario").exists())
                .andExpect(jsonPath("$[0].contenido").exists());
    }

    @Test
    void testObtenerMensajesConversacionInexistente() throws Exception {
        mockMvc.perform(get("/api/mensajes/usuarioInexistente1/usuarioInexistente2")
                .header("Authorization", "no Auth"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testObtenerMensajesConParametrosVacios() throws Exception {
        mockMvc.perform(get("/api/mensajes/ / ")
                .header("Authorization", "no Auth"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testObtenerMensajesConUsuariosMismos() throws Exception {
        Mensaje mensaje = Mensaje.builder()
                .idRemitente("usuario1")
                .idDestinatario("usuario1")
                .contenido("Mensaje a mí mismo")
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        servicioMensaje.save(mensaje);

        mockMvc.perform(get("/api/mensajes/usuario1/usuario1")
                .header("Authorization", "no Auth"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].contenido").value("Mensaje a mí mismo"));
    }

    @Test
    void testObtenerMensajesConUsuariosInvertidos() throws Exception {
        Mensaje mensaje1 = Mensaje.builder()
                .idRemitente("usuario1")
                .idDestinatario("usuario2")
                .contenido("De usuario1 a usuario2")
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        servicioMensaje.save(mensaje1);

        mockMvc.perform(get("/api/mensajes/usuario2/usuario1")
                .header("Authorization", "no Auth"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].contenido").value("De usuario1 a usuario2"));
    }

    @Test
    void testObtenerMensajesRespuestaFormatoCorrect() throws Exception {
        Mensaje mensaje = Mensaje.builder()
                .idRemitente("usuario1")
                .idDestinatario("usuario2")
                .contenido("Mensaje de prueba")
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        servicioMensaje.save(mensaje);

        mockMvc.perform(get("/api/mensajes/usuario1/usuario2")
                .header("Authorization", "no Auth"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].idRemitente").value("usuario1"))
                .andExpect(jsonPath("$[0].idDestinatario").value("usuario2"))
                .andExpect(jsonPath("$[0].contenido").value("Mensaje de prueba"))
                .andExpect(jsonPath("$[0].fechaEnvio").exists())
                .andExpect(jsonPath("$[0].leido").exists())
                .andExpect(jsonPath("$[0].conversacionId").exists());
    }
}
