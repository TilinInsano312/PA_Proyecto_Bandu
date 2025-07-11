package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.UsuarioDTO;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ServicioUsuarioTest {
    @Autowired
    private ServicioUsuario servicioUsuario;

    @AfterEach
    void limpiarDatos() {
        servicioUsuario.findAll().forEach(usuario -> servicioUsuario.delete(usuario.id()));
    }

    @Test
    void buscarPorIdTest() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("10", "testUser", "password123", "");
        servicioUsuario.save(usuarioDTO);
        assertEquals("10", servicioUsuario.buscarPorId("10").id());
    }

    @Test
    void guardarUsuarioTest() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("10", "testUser", "password123", "");
        assertEquals(usuarioDTO, servicioUsuario.save(usuarioDTO));

    }

    @Test
    void testListarUsuarios() {
        UsuarioDTO usuario1 = new UsuarioDTO("1", "user1", "pass1", "a");
        UsuarioDTO usuario2 = new UsuarioDTO("2", "user2", "pass2", "b");
        servicioUsuario.save(usuario1);
        servicioUsuario.save(usuario2);
        assertEquals(2, servicioUsuario.findAll().size());
    }
    @Test
    void testEliminarUsuario() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("10", "testUser", "password123", "");
        servicioUsuario.save(usuarioDTO);
        assertEquals(usuarioDTO, servicioUsuario.buscarPorId("10"));
        servicioUsuario.delete("10");
        assertThrows(Exception.class, () -> servicioUsuario.buscarPorId("10"));
    }

    @Test
    void testModificarContrasena() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("10", "testUser", "password123", "");
        servicioUsuario.save(usuarioDTO);
        servicioUsuario.modificarContrasena("10", "newPassword123");
        assertEquals("newPassword123", servicioUsuario.buscarPorId("10").contrasena());
    }
    @Test
    void testModificarEmail() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("10", "testUser", "password123", "");
        servicioUsuario.save(usuarioDTO);
        servicioUsuario.modificarEmail("10", "2asd@example.com");
        assertEquals("2asd@example.com", servicioUsuario.buscarPorId("10").email());
    }

}
