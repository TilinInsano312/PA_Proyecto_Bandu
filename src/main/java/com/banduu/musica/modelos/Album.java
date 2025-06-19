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
@EqualsAndHashCode
@ToString
public class Album {

    private String nombreAlbum;
    private String artistaAlbum;
    private String generoAlbum;
    private String imagenAlbum;

    public Album(String nombreAlbum, String artistaAlbum, String generoAlbum, String imagenAlbum) {
        this.nombreAlbum = nombreAlbum;
        this.artistaAlbum = artistaAlbum;
        this.generoAlbum = generoAlbum;
        this.imagenAlbum = imagenAlbum;
    }

    public Album() {
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public String getArtistaAlbum() {
        return artistaAlbum;
    }

    public String getGeneroAlbum() {
        return generoAlbum;
    }

    public String getImagenAlbum() {
        return imagenAlbum;
    }
}
