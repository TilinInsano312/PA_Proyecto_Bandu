package com.banduu.musica.modelos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "albums")
public class Album {

    @Id
    private String id;

    private String nombreAlbum;
    private String artistaAlbum;
    private String generoAlbum;
    private String imagenAlbum;

}
