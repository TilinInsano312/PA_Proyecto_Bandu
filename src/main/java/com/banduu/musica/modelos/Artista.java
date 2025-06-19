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

@EqualsAndHashCode
@ToString
public class Artista {

    private String nombreArtista;
    private String generoArtista;
    private String imagenArtista;

    public Artista(String nombreArtista, String generoArtista, String imagenArtista) {
        this.nombreArtista = nombreArtista;
        this.generoArtista = generoArtista;
        this.imagenArtista = imagenArtista;
    }

    public Artista() {
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public String getGeneroArtista() {
        return generoArtista;
    }

    public String getImagenArtista() {
        return imagenArtista;
    }
}
