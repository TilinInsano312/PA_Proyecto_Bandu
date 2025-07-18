package com.banduu.usuario.controladores;

import com.banduu.usuario.repositorios.impl.ImplementacionEmparejamiento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
/**
 * Controlador para gestionar las operaciones de emparejamiento de usuarios en la aplicacion.
 * Permite obtener emparejamientos basados en diferentes criterios como album, artista, cancion, carrera, orientacion, genero, etc.
 *
 * @author Vicente Salazar
 * @version 1.0
 */
@RestController
@RequestMapping("/api/emparejamiento")
public class ControladorEmparejamiento {
    private final ImplementacionEmparejamiento servicioEmparejamiento;
    public ControladorEmparejamiento(ImplementacionEmparejamiento servicioEmparejamiento) {
        this.servicioEmparejamiento = servicioEmparejamiento;
    }

    @GetMapping("/album/{idUsuario}")
    public ResponseEntity<Map<String, Integer>> emparejamientoPorAlbum(@PathVariable String idUsuario) {
        return ResponseEntity.ok(servicioEmparejamiento.porAlbum(idUsuario));
    }
    @GetMapping("/artista/{idUsuario}")
    public ResponseEntity<Map<String, Integer>> emparejamientoPorArtista(@PathVariable String idUsuario) {
        return ResponseEntity.ok(servicioEmparejamiento.porArtista(idUsuario));
    }
    @GetMapping("/cancion/{idUsuario}")
    public ResponseEntity<Map<String, Integer>> emparejamientoPorCancion(@PathVariable String idUsuario) {
        return ResponseEntity.ok(servicioEmparejamiento.porCancion(idUsuario));
    }
    @GetMapping("/carrera/{idUsuario}")
    public ResponseEntity<Map<String, Integer>> emparejamientoPorCarrera(@PathVariable String idUsuario) {
        return ResponseEntity.ok(servicioEmparejamiento.porCarrera(idUsuario));
    }
    @GetMapping("/orientacion/{idUsuario}")
    public ResponseEntity<Map<String, Integer>> emparejamientoPorOrientacion(@PathVariable String idUsuario) {
        return ResponseEntity.ok(servicioEmparejamiento.porOrientacion(idUsuario));
    }
    @GetMapping("/genero/{idUsuario}")
    public ResponseEntity<Map<String, Integer>> emparejamiento(@PathVariable String idUsuario) {
        return ResponseEntity.ok(servicioEmparejamiento.porGenero(idUsuario));
    }
    @GetMapping("/generosmusicales/{idUsuario}")
    public ResponseEntity<Map<String, Integer>> emparejamientoPorGenerosMusicales(@PathVariable String idUsuario) {
        return ResponseEntity.ok(servicioEmparejamiento.porGenerosMusicales(idUsuario));
    }
    @GetMapping("/descartar/{idUsuario}")
    public ResponseEntity<List<String>> blacklistPorMismaCarrera(@PathVariable String idUsuario) {
        return ResponseEntity.ok(servicioEmparejamiento.descartarMismaCarrera(idUsuario));
    }

}
