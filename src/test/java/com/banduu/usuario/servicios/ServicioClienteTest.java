package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.ClienteDTO;
import com.banduu.usuario.dto.UsuarioDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class ServicioClienteTest {

    @Autowired
    private ServicioUsuario servicioUsuario;
    @Autowired
    private ServicioCliente servicioCliente;

    @BeforeEach
    public void limpiar() {
        servicioCliente.findAll().forEach(cliente -> servicioCliente.delete(cliente.id()));
    }
    @AfterEach
    public void limpiarDatos() {
        servicioCliente.findAll().forEach(cliente -> servicioCliente.delete(cliente.id()));
        servicioUsuario.findAll().forEach(usuario -> servicioUsuario.delete(usuario.id()));
    }
    @Test
    public void testCrearCliente() {
        ClienteDTO clienteDTO = new ClienteDTO("20", "a","","",10,"","","", List.of(),List.of(),List.of(),List.of());
        assertEquals(clienteDTO, servicioCliente.save(clienteDTO));
    }
    @Test
    public void testListarClientes() {
        ClienteDTO cliente1 = new ClienteDTO("1", "cliente1","","",10,"","","",List.of(),List.of(),List.of(),List.of());
        ClienteDTO cliente2 = new ClienteDTO("2", "cliente2","","",20,"","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        assertEquals(2, servicioCliente.findAll().size());
    }
    @Test
    public void testBuscarClientePorId() {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        assertEquals(clienteDTO, servicioCliente.buscarPorId("1"));
    }
    @Test
    public void testEliminarCliente() {
        // Aquí puedes agregar la lógica para probar la eliminación de un cliente
        // Por ejemplo, eliminar un cliente y verificar que ya no esté en la lista
    }

}
