package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.UsuarioDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

//@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
public class ServicioUsuarioTest {

//    @Container
//    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.17")
//            .withExposedPorts(27018);
//
//    @DynamicPropertySource
//    static void mongoProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
//        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//    }

    @Autowired
    private ServicioUsuario servicioUsuario;

    @AfterEach
    public void limpiarDatos() {
        servicioUsuario.findAll().forEach(usuario -> servicioUsuario.delete(usuario.id()));
    }

    @Test
    public void buscarPorIdTest() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("10", "testUser", "password123", "");
        servicioUsuario.save(usuarioDTO);
        assertEquals("10", servicioUsuario.buscarPorId("10").id());
    }

    @Test
    public void guardarUsuarioTest() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("10", "testUser", "password123", "");
        servicioUsuario.save(usuarioDTO);
        assertEquals(usuarioDTO, servicioUsuario.save(usuarioDTO));

    }

    @Test
    public void testListarUsuarios() {
        UsuarioDTO usuario1 = new UsuarioDTO("1", "user1", "pass1", "a");
        UsuarioDTO usuario2 = new UsuarioDTO("2", "user2", "pass2", "b");
        servicioUsuario.save(usuario1);
        servicioUsuario.save(usuario2);
        assertEquals(2, servicioUsuario.findAll().size());
    }
    @Test
    public void testEliminarUsuario() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("10", "testUser", "password123", "");
        servicioUsuario.save(usuarioDTO);
        assertEquals(usuarioDTO, servicioUsuario.buscarPorId("10"));
        servicioUsuario.delete("10");
        assertNull(servicioUsuario.buscarPorId("10"));
    }

}
