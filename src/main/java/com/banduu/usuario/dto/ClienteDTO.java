package com.banduu.usuario.dto;

import com.banduu.musica.modelos.Album;
import com.banduu.musica.modelos.Artista;
import com.banduu.musica.modelos.Cancion;

import java.util.List;
/**
 * Clase que representa un cliente en el sistema.
 * Contiene atributos como id, idUsuario, nombre, apellido, edad, imagen, carrera, orientacion, genero,
 * generosMusicales, canciones, artistas y albums.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 2.0
 */
public record ClienteDTO(
         String id,
         String idUsuario,
         String nombre,
         String apellido,
         int edad,
         String imagen,
         String carrera,
         String orientacion,
         String genero,
         List<String>generosMusicales,
         List<Cancion> canciones,
         List<Artista> artistas,
         List<Album> albums

) {
}
