package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.ClienteDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ServicioClienteTest {

    @Autowired
    private ServicioUsuario servicioUsuario;
    @Autowired
    private ServicioCliente servicioCliente;

    @BeforeEach
    void limpiar() {
        servicioCliente.findAll().forEach(cliente -> servicioCliente.delete(cliente.id()));
    }
    @AfterEach
    void limpiarDatos() {
        servicioCliente.findAll().forEach(cliente -> servicioCliente.delete(cliente.id()));
        servicioUsuario.findAll().forEach(usuario -> servicioUsuario.delete(usuario.id()));
    }
    @Test
    void testCrearCliente() {
        ClienteDTO clienteDTO = new ClienteDTO("20", "a","","",10,"","","", List.of(),List.of(),List.of(),List.of());
        assertEquals(clienteDTO, servicioCliente.save(clienteDTO));
    }
    @Test
    void testListarClientes() {
        ClienteDTO cliente1 = new ClienteDTO("1", "cliente1","","",10,"","","",List.of(),List.of(),List.of(),List.of());
        ClienteDTO cliente2 = new ClienteDTO("2", "cliente2","","",20,"","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(cliente1);
        servicioCliente.save(cliente2);
        assertEquals(2, servicioCliente.findAll().size());
    }
    @Test
    void testBuscarClientePorId() {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        assertEquals(clienteDTO, servicioCliente.buscarPorId("1"));
    }
    @Test
    void testEliminarCliente() {
        ClienteDTO clienteDTO = new ClienteDTO("1", "cliente1","","",10,"","","",List.of(),List.of(),List.of(),List.of());
        servicioCliente.save(clienteDTO);
        assertEquals(clienteDTO, servicioCliente.buscarPorId("1"));
        servicioCliente.delete("1");
        assertThrows(Exception.class, () -> servicioCliente.buscarPorId("1"));
    }

}
