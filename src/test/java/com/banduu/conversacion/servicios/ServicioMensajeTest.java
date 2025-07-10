package com.banduu.conversacion.servicios;

import com.banduu.conversacion.modelos.Mensaje;
import com.banduu.conversacion.repositorios.RepositorioMensaje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ServicioMensajeTest {
    
    @Autowired
    private ServicioMensaje servicioMensaje;
    
    @Autowired
    private RepositorioMensaje repositorioMensaje;

    @Autowired
    private com.banduu.conversacion.repositorios.RepositorioConversacion repositorioConversacion;

    @AfterEach
    void limpiarDatos() {
        repositorioMensaje.deleteAll();
        repositorioConversacion.deleteAll();
    }

    @Test
    void testGuardarMensaje() {
        Mensaje mensaje = Mensaje.builder()
                .idRemitente("usuario1")
                .idDestinatario("usuario2")
                .contenido("Hola, ¿cómo estas?")
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        Mensaje mensajeGuardado = servicioMensaje.save(mensaje);

        assertNotNull(mensajeGuardado);
        assertNotNull(mensajeGuardado.getConversacionId());
        assertEquals("usuario1", mensajeGuardado.getIdRemitente());
        assertEquals("usuario2", mensajeGuardado.getIdDestinatario());
        assertEquals("Hola, ¿cómo estas?", mensajeGuardado.getContenido());
        assertFalse(mensajeGuardado.isLeido());
    }

    @Test
    void testObtenerMensajesConversacionExistente() {
        Mensaje mensaje1 = Mensaje.builder()
                .idRemitente("usuario1")
                .idDestinatario("usuario2")
                .contenido("Primer mensaje")
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        Mensaje mensaje2 = Mensaje.builder()
                .idRemitente("usuario2")
                .idDestinatario("usuario1")
                .contenido("Respuesta al primer mensaje")
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        servicioMensaje.save(mensaje1);
        servicioMensaje.save(mensaje2);

        List<Mensaje> mensajes = servicioMensaje.obtenerMensajes("usuario1", "usuario2");

        assertEquals(2, mensajes.size());
        assertTrue(mensajes.stream().anyMatch(m -> m.getContenido().equals("Primer mensaje")));
        assertTrue(mensajes.stream().anyMatch(m -> m.getContenido().equals("Respuesta al primer mensaje")));
    }

    @Test
    void testObtenerMensajesConversacionInexistente() {
        List<Mensaje> mensajes = servicioMensaje.obtenerMensajes("usuarioInexistente1", "usuarioInexistente2");

        assertTrue(mensajes.isEmpty());
    }

    @Test
    void testGuardarMensajeConMismaConversacion() {
        Mensaje mensaje1 = Mensaje.builder()
                .idRemitente("usuario1")
                .idDestinatario("usuario2")
                .contenido("Primer mensaje")
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        Mensaje mensaje2 = Mensaje.builder()
                .idRemitente("usuario1")
                .idDestinatario("usuario2")
                .contenido("Segundo mensaje")
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        Mensaje mensajeGuardado1 = servicioMensaje.save(mensaje1);
        Mensaje mensajeGuardado2 = servicioMensaje.save(mensaje2);

        assertNotNull(mensajeGuardado1.getConversacionId());
        assertNotNull(mensajeGuardado2.getConversacionId());
        assertEquals(mensajeGuardado1.getConversacionId(), mensajeGuardado2.getConversacionId());
    }

    @Test
    void testGuardarMensajeConUsuariosInvertidos() {
        Mensaje mensaje1 = Mensaje.builder()
                .idRemitente("usuario1")
                .idDestinatario("usuario2")
                .contenido("Mensaje de usuario1 a usuario2")
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        Mensaje mensaje2 = Mensaje.builder()
                .idRemitente("usuario2")
                .idDestinatario("usuario1")
                .contenido("Respuesta de usuario2 a usuario1")
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        Mensaje mensajeGuardado1 = servicioMensaje.save(mensaje1);
        Mensaje mensajeGuardado2 = servicioMensaje.save(mensaje2);

        assertEquals(mensajeGuardado1.getConversacionId(), mensajeGuardado2.getConversacionId());
    }
}
