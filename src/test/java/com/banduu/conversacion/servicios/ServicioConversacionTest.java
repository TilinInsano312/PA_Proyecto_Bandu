package com.banduu.conversacion.servicios;

import com.banduu.conversacion.repositorios.RepositorioConversacion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ServicioConversacionTest {
    
    @Autowired
    private ServicioConversacion servicioConversacion;
    
    @Autowired
    private RepositorioConversacion repositorioConversacion;

    @AfterEach
    void limpiarDatos() {
        repositorioConversacion.deleteAll();
    }

    @Test
    void testObtenerIdConversacionCreandoSiNoExiste() {
        Optional<String> idConversacion = servicioConversacion.obtenerIdConversacion("usuario1", "usuario2", true);

        assertTrue(idConversacion.isPresent());
        assertNotNull(idConversacion.get());
        assertTrue(idConversacion.get().contains("usuario1"));
        assertTrue(idConversacion.get().contains("usuario2"));
    }

    @Test
    void testObtenerIdConversacionExistente() {
        Optional<String> idConversacionCreada = servicioConversacion.obtenerIdConversacion("usuario1", "usuario2", true);
        assertTrue(idConversacionCreada.isPresent());

        Optional<String> idConversacionObtenida = servicioConversacion.obtenerIdConversacion("usuario1", "usuario2", false);

        assertTrue(idConversacionObtenida.isPresent());
        assertEquals(idConversacionCreada.get(), idConversacionObtenida.get());
    }

    @Test
    void testFormatoIdConversacionOrdenInvertido() {
        Optional<String> idConversacion = servicioConversacion.obtenerIdConversacion("zzz", "aaa", true);

        assertTrue(idConversacion.isPresent());
        assertEquals("aaa-zzz", idConversacion.get());
    }
}
