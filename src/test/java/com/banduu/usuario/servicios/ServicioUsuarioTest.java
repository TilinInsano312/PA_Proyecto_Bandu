package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.UsuarioDTO;
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

    @Test
    public void buscarPorIdTest() {

    }

    @Test
    public void guardarUsuarioTest() {
        servicioUsuario.save(new UsuarioDTO("3", "asdsad", "Test", "User"));
        assertNotNull(servicioUsuario, "El servicio de usuario no debe ser nulo");
    }

    @Test
    public void testListarUsuarios() {
        // Aquí puedes implementar la lógica para probar el método listarUsuarios
        // Por ejemplo, verificar que la lista no sea nula o que contenga elementos esperados
    }
}
