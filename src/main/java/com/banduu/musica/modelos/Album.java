package com.banduu.musica.modelos;

import lombok.*;

/**
 * Clase que representa un álbum en el sistema.
 * Contiene atributos como nombre, artista, genero e imagen del álbum.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Album {
    private String nombreAlbum;
    private String artistaAlbum;
    private String generoAlbum;
    private String imagenAlbum;

}
