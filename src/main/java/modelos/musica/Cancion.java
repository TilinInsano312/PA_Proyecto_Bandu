package modelos.musica;

import java.util.Objects;

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

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public String getArtistaCancion() {
        return artistaCancion;
    }

    public void setArtistaCancion(String artistaCancion) {
        this.artistaCancion = artistaCancion;
    }

    public String getGeneroCancion() {
        return generoCancion;
    }

    public void setGeneroCancion(String generoCancion) {
        this.generoCancion = generoCancion;
    }

    public String getImagenCancion() {
        return imagenCancion;
    }

    public void setImagenCancion(String imagenCancion) {
        this.imagenCancion = imagenCancion;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "nombreCancion='" + nombreCancion + '\'' +
                ", artistaCancion='" + artistaCancion + '\'' +
                ", generoCancion='" + generoCancion + '\'' +
                ", imagenCancion='" + imagenCancion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cancion cancion = (Cancion) o;
        return Objects.equals(nombreCancion, cancion.nombreCancion) && Objects.equals(artistaCancion, cancion.artistaCancion) && Objects.equals(generoCancion, cancion.generoCancion) && Objects.equals(imagenCancion, cancion.imagenCancion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreCancion, artistaCancion, generoCancion, imagenCancion);
    }
}
