package com.banduu.musica.controlador;

import com.banduu.musica.dto.AlbumDTO;
import com.banduu.musica.dto.ArtistaDTO;
import com.banduu.musica.dto.CancionDTO;
import com.banduu.musica.servicios.ServicioMusica;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/musica")
public class ControladorMusica {
    private ServicioMusica servicioMusica;
    public ControladorMusica(ServicioMusica servicioMusica) {
        this.servicioMusica = servicioMusica;
    }

    @GetMapping("/cancion/{q}")
    public ResponseEntity<CancionDTO> getMusica(@PathVariable String q) {
        return ResponseEntity.ok(servicioMusica.buscarCancion(q));
    }
    @GetMapping("/artista/{q}")
    public ResponseEntity<ArtistaDTO> getArtista(@PathVariable String q) {
        return ResponseEntity.ok(servicioMusica.buscarArtista(q));
    }
    @GetMapping("/album/{q}")
    public ResponseEntity<AlbumDTO> getAlbum(@PathVariable String q) {
        return ResponseEntity.ok(servicioMusica.buscarAlbum(q));
    }

}
