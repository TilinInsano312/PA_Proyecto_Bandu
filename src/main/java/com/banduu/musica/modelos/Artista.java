package com.banduu.musica.modelos;

import lombok.*;

/**
 * Clase que representa un artista en el sistema.
 * Contiene atributos como nombre, genero e imagen del artista.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Artista {
    private String nombreArtista;
    private String generoArtista;
    private String imagenArtista;

}
