package com.banduu.musica.modelos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "canciones")
public class Cancion {

    @Id
    private String id;

    private String nombreCancion;
    private String artistaCancion;
    private String generoCancion;
    private String imagenCancion;

}
