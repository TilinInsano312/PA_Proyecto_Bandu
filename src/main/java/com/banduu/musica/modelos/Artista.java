package com.banduu.musica.modelos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "artistas")
public class Artista {

    @Id
    private String id;

    private String nombreArtista;
    private String generoArtista;
    private String imagenArtista;

}
