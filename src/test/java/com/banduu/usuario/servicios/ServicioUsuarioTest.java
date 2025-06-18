package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.UsuarioDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ServicioUsuarioTest {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Test
    public void buscarPorIdTest() {

    }

    @Test
    public void guardarUsuarioTest() {
        servicioUsuario.save(new UsuarioDTO("", "111111111", "Test", "User"));
        assertNotNull(servicioUsuario, "El servicio de usuario no debe ser nulo");
    }

    @Test
    public void testListarUsuarios() {
        // Aquí puedes implementar la lógica para probar el método listarUsuarios
        // Por ejemplo, verificar que la lista no sea nula o que contenga elementos esperados
    }
}
