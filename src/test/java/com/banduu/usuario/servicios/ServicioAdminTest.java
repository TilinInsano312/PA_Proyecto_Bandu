package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.AdminDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest
public class ServicioAdminTest {

    @Autowired
    private ServicioAdmin servicioAdmin;

    @Autowired
    private ServicioUsuario servicioUsuario;

    @AfterEach
    public void limpiarDatos() {
        servicioAdmin.findAll().forEach(admin -> servicioAdmin.delete(admin.id()));
        servicioUsuario.findAll().forEach(usuario -> servicioUsuario.delete(usuario.id()));
    }

    @Test
    public void testCrearAdmin() {
        AdminDTO adminDTO = new AdminDTO("1", "adminUser", "adminPass");
        assertEquals(adminDTO, servicioAdmin.save(adminDTO));
    }
    @Test
    public void testListarAdmins() {
        AdminDTO admin1 = new AdminDTO("1", "admin1", "pass1");
        AdminDTO admin2 = new AdminDTO("2", "admin2", "pass2");
        servicioAdmin.save(admin1);
        servicioAdmin.save(admin2);
        assertEquals(2, servicioAdmin.findAll().size());
    }
    @Test
    public void testEliminarAdmin() {
        // Aquí puedes agregar la lógica para probar la eliminación de un administrador
        // Por ejemplo, eliminar un administrador y verificar que ya no esté en la lista
    }
}
