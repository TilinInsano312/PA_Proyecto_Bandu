package com.banduu.modelos.musica;

import java.util.Objects;

/**
 * Clase que representa un álbum en el sistema.
 * Contiene atributos como nombre, artista, genero e imagen del álbum.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */

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

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public String getArtistaAlbum() {
        return artistaAlbum;
    }

    public void setArtistaAlbum(String artistaAlbum) {
        this.artistaAlbum = artistaAlbum;
    }

    public String getGeneroAlbum() {
        return generoAlbum;
    }

    public void setGeneroAlbum(String generoAlbum) {
        this.generoAlbum = generoAlbum;
    }

    public String getImagenAlbum() {
        return imagenAlbum;
    }

    public void setImagenAlbum(String imagenAlbum) {
        this.imagenAlbum = imagenAlbum;
    }

    @Override
    public String toString() {
        return "Album{" +
                "nombreAlbum='" + nombreAlbum + '\'' +
                ", artistaAlbum='" + artistaAlbum + '\'' +
                ", generoAlbum='" + generoAlbum + '\'' +
                ", imagenAlbum='" + imagenAlbum + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(nombreAlbum, album.nombreAlbum) && Objects.equals(artistaAlbum, album.artistaAlbum) && Objects.equals(generoAlbum, album.generoAlbum) && Objects.equals(imagenAlbum, album.imagenAlbum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreAlbum, artistaAlbum, generoAlbum, imagenAlbum);
    }
}
