package modelos.musica;

import java.util.Objects;

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

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getGeneroArtista() {
        return generoArtista;
    }

    public void setGeneroArtista(String generoArtista) {
        this.generoArtista = generoArtista;
    }

    public String getImagenArtista() {
        return imagenArtista;
    }

    public void setImagenArtista(String imagenArtista) {
        this.imagenArtista = imagenArtista;
    }

    @Override
    public String toString() {
        return "Artista{" +
                "nombreArtista='" + nombreArtista + '\'' +
                ", generoArtista='" + generoArtista + '\'' +
                ", imagenArtista='" + imagenArtista + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artista artista = (Artista) o;
        return Objects.equals(nombreArtista, artista.nombreArtista) && Objects.equals(generoArtista, artista.generoArtista) && Objects.equals(imagenArtista, artista.imagenArtista);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreArtista, generoArtista, imagenArtista);
    }
}
