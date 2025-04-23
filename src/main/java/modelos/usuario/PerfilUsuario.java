package modelos.usuario;

import modelos.musica.Album;
import modelos.musica.Artista;
import modelos.musica.Cancion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PerfilUsuario extends Usuario {

	private String nombre;
	private String apellida;
	private int edad;
	private String carrera;
	private boolean mismaCarrera;
	private String orientacion;
	private String genero;

	List<Cancion> canciones;
	List<Artista> artistas;
	List<Album> albums;

	public PerfilUsuario(String usuario, String contrasena, String email, String nombre, String apellida, int edad, String orientacion,
						 String carrera, boolean mismaCarrera, String genero) {
		super(usuario, contrasena, email);
		this.nombre = nombre;
		this.apellida = apellida;
		this.edad = edad;
		this.orientacion = orientacion;
		this.carrera = carrera;
		this.mismaCarrera = mismaCarrera;
		this.genero = genero;
		this.canciones=new ArrayList<>();
		this.artistas=new ArrayList<>();
		this.albums=new ArrayList<>();
	}

	public PerfilUsuario() {
	}
	public void agregarAlbum(Album album){
		this.albums.add(album);
	}
	public void agregarArtistas(Artista artista){
		this.artistas.add(artista);
	}
	public void agregarCanciones(Cancion cancion){
		this.canciones.add(cancion);
	}

	public List<Artista> getArtistas() {
		return artistas;
	}

	public void setArtistas(List<Artista> artistas) {
		this.artistas = artistas;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	public List<Cancion> getCanciones() {
		return canciones;
	}

	public void setCanciones(List<Cancion> canciones) {
		this.canciones = canciones;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellida() {
		return apellida;
	}

	public void setApellida(String apellida) {
		this.apellida = apellida;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public boolean isMismaCarrera() {
		return mismaCarrera;
	}

	public void setMismaCarrera(boolean mismaCarrera) {
		this.mismaCarrera = mismaCarrera;
	}

	public String getOrientacion() {
		return orientacion;
	}

	public void setOrientacion(String orientacion) {
		this.orientacion = orientacion;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	@Override
	public String toString() {
		return super.toString() +
				"PerfilUsuario{" +
				"nombre='" + nombre + '\'' +
				", apellida='" + apellida + '\'' +
				", edad=" + edad +
				", carrera='" + carrera + '\'' +
				", mismaCarrera=" + mismaCarrera +
				", orientacion='" + orientacion + '\'' +
				", genero='" + genero + '\'' +
				", albums=" + albums +
				", artistas=" + artistas +
				", canciones=" + canciones +
				"} ";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		PerfilUsuario that = (PerfilUsuario) o;
		return edad == that.edad && mismaCarrera == that.mismaCarrera && Objects.equals(nombre, that.nombre) && Objects.equals(apellida, that.apellida) && Objects.equals(carrera, that.carrera) && Objects.equals(orientacion, that.orientacion) && Objects.equals(genero, that.genero);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), nombre, apellida, edad, carrera, mismaCarrera, orientacion, genero);
	}

}