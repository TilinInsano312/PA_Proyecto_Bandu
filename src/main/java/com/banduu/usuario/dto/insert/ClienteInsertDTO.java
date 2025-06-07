package com.banduu.usuario.dto.insert;

import com.banduu.musica.modelos.Album;
import com.banduu.musica.modelos.Artista;
import com.banduu.musica.modelos.Cancion;

import java.util.List;

public record ClienteInsertDTO(String id,
                               String nombreUsuario,
                               String contrasena,
                               String email,
                               String nombre,
                               String apellido,
                               int edad,
                               String carrera,
                               boolean mismaCarrera,
                               String orientacion,
                               String genero,
                               List<String> generosMusicales,
                               List<Cancion> canciones,
                               List<Artista> artistas,
                               List<Album> albums) {
}
