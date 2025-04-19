package modelos.musica;

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
}
