package com.banduu.musica.modelos;

import lombok.*;

/**
 * Clase que representa una cancion en el sistema.
 * Contiene atributos como nombre, artista, genero e imagen de la canción.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Cancion {
    private String nombreCancion;
    private String artistaCancion;
    private String generoCancion;
    private String imagenCancion;

}
