package com.banduu.musica.modelos;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa una cancion en el sistema.
 * Contiene atributos como nombre, artista, genero e imagen de la canción.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */

@EqualsAndHashCode
@ToString
public class Cancion {


    private String nombreCancion;
    private String artistaCancion;
    private String generoCancion;
    private String imagenCancion;

    public Cancion(String nombreCancion, String artistaCancion, String generoCancion, String imagenCancion) {
        this.nombreCancion = nombreCancion;
        this.artistaCancion = artistaCancion;
        this.generoCancion = generoCancion;
        this.imagenCancion = imagenCancion;
    }

    public Cancion() {
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public String getArtistaCancion() {
        return artistaCancion;
    }

    public String getGeneroCancion() {
        return generoCancion;
    }

    public String getImagenCancion() {
        return imagenCancion;
    }
}
