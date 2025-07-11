package com.banduu.musica.dto;
/**
 * Clase que representa un album en el sistema.
 * Contiene atributos como nombre, artista, genero e imagen de la cancion.
 *
 * @author Vicente Salazar
 * @version 1.0
 */
public record CancionDTO(String nombreCancion,String artistaCancion, String generoCancion, String imagenCancion) {
}
