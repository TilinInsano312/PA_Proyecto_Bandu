package com.banduu.sistema;

import com.banduu.modelos.denuncia.Denuncia;
import com.banduu.modelos.usuario.Cliente;
import com.banduu.sistema.GestorArchivos;
import com.banduu.sistema.SistemaDenuncia;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SistemaDenunciaTest {
    Cliente perfilUsuario = new Cliente("usuario1", "contrasena1", "email1", "nombre1", "apellido1", 20, "orientacion1", "carrera", true, "genero1");
    Denuncia denuncia = new Denuncia(perfilUsuario, "Texto de ejemplo", "Contenido ofensivo");

    @Test
    void validarDenuncia() {
        SistemaDenuncia.validarDenuncia(denuncia,true);
        assertEquals(denuncia, GestorArchivos.leerListaObjetos("Denuncia.json", Denuncia.class).get(0));
    }
}