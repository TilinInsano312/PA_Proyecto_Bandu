package modelos.usuario;

import modelos.musica.Album;
import modelos.musica.Artista;
import modelos.musica.Cancion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PerfilUsuario extends Usuario {

	private String nombre;
	private String apellido;
	private int edad;
	private String carrera;
	private boolean mismaCarrera;
	private String orientacion;
	private String genero;
	private List<String> generosMusicales;

	List<Cancion> canciones;
	List<Artista> artistas;
	List<Album> albums;

	public PerfilUsuario(String usuario, String contrasena, String email, String nombre, String apellido, int edad, String orientacion,
						 String carrera, boolean mismaCarrera, String genero) {
		super(usuario, contrasena, email);
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.orientacion = orientacion;
		this.carrera = carrera;
		this.mismaCarrera = mismaCarrera;
		this.genero = genero;
		this.generosMusicales = new ArrayList<>();
		this.canciones=new ArrayList<>();
		this.artistas=new ArrayList<>();
		this.albums=new ArrayList<>();
	}


	public PerfilUsuario() {
	}
	// agregar a la lista
	public void agregarGenerosMusicales(String generoMusical){
		this.generosMusicales.add(generoMusical);
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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

	public List<String> getGenerosMusicales() {
		return generosMusicales;
	}
	public void setGenerosMusicales(List<String> generosMusicales) {
		this.generosMusicales = generosMusicales;
	}

	@Override
	public String toString() {
		return super.toString() +
				"PerfilUsuario{" +
				"nombre='" + nombre + '\'' +
				", apellido='" + apellido + '\'' +
				", edad=" + edad +
				", carrera='" + carrera + '\'' +
				", mismaCarrera=" + mismaCarrera +
				", orientacion='" + orientacion + '\'' +
				", genero='" + genero + '\'' +
				", generosMusicales='" + generosMusicales + '\'' +
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
		return edad == that.edad && mismaCarrera == that.mismaCarrera && Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido) && Objects.equals(carrera, that.carrera) && Objects.equals(orientacion, that.orientacion) && Objects.equals(genero, that.genero) && Objects.equals(generosMusicales, that.generosMusicales) && Objects.equals(canciones, that.canciones) && Objects.equals(artistas, that.artistas) && Objects.equals(albums, that.albums);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), nombre, apellido, edad, carrera, mismaCarrera, orientacion, genero, generosMusicales, canciones, artistas, albums);
	}
}