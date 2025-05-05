package sistema;

import modelos.musica.Cancion;
import modelos.usuario.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorArchivosTest {
    Cancion cancion = new Cancion("Despacito", "Luis Fonsi", "Pop", "https://example.com/despacito");
    Cliente perfilUsuario = new Cliente( "usuario1", "contrasena1", "email1", "nombre1", "apellido1", 20, "orientacion1", "carrera", true, "genero1");
    List<Cliente> listaClientes = new ArrayList<>();

    @BeforeEach
    void setUp() {
        perfilUsuario.agregarCanciones(cancion);
        listaClientes.add(perfilUsuario);
    }
    @Test
    void escribirListaObjetos() {
        assertDoesNotThrow(() -> GestorArchivos.escribirListaObjetos(listaClientes, Cliente.class));
    }

    @Test
    void leerListaObjetos() {
        GestorArchivos.escribirListaObjetos(listaClientes, Cliente.class);
        assertEquals( listaClientes, GestorArchivos.leerListaObjetos("Cliente.json",Cliente.class));
    }

    @Test
    void borrarObjeto() {
        GestorArchivos.escribirListaObjetos(listaClientes, Cliente.class);
        assertDoesNotThrow(() -> GestorArchivos.borrarObjeto("Cliente.json", Cliente.class, perfilUsuario));

    }

    @Test
    void modificarUnAtributoPerfilUsuario() {
        GestorArchivos.escribirListaObjetos(listaClientes, Cliente.class);
        assertAll(
                () -> assertDoesNotThrow(() -> GestorArchivos.modificarUnAtributoPerfilUsuario(perfilUsuario, "nombre", "nuevoNombre")),
                () -> assertEquals("nuevoNombre", perfilUsuario.getNombre())
        );
    }
}