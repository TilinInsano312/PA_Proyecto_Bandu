package com.banduu.usuario.dto.abreviado;

import com.banduu.musica.modelos.Album;
import com.banduu.musica.modelos.Artista;
import com.banduu.musica.modelos.Cancion;

import java.util.List;

public record ClienteDTO(
         String id,
         String nombre,
         String apellido,
         int edad,
         String carrera,
         boolean mismaCarrera,
         String orientacion,
         String genero,
         List<String>generosMusicales,
         List<Cancion> canciones,
         List<Artista> artistas,
         List<Album> albums

) {
}
