package com.banduu.usuario.modelos;

import com.banduu.musica.modelos.Album;
import com.banduu.musica.modelos.Artista;
import com.banduu.musica.modelos.Cancion;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Clase que representa un cliente en el sistema.
 * Hereda de la clase Usuario y contiene atributos adicionales como nombre, apellido, edad, carrera, etc.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 2.0
 */

@EqualsAndHashCode(callSuper = true)
@ToString
@Document(collection = "clientes")
public class Cliente extends Usuario {

	public Cliente() {
	}

	public Cliente(String nombre, String apellido, int edad, String carrera,  boolean mismaCarrera, String orientacion, String genero, List<String> generosMusicales, List<Cancion> canciones, List<Artista> artistas, List<Album> albums) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.carrera = carrera;
		this.mismaCarrera = mismaCarrera;
		this.orientacion = orientacion;
		this.genero = genero;
		this.generosMusicales = generosMusicales;
		this.canciones = canciones;
		this.artistas = artistas;
		this.albums = albums;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public int getEdad() {
		return edad;
	}

	public String getCarrera() {
		return carrera;
	}

	public boolean isMismaCarrera() {
		return mismaCarrera;
	}

	public String getOrientacion() {
		return orientacion;
	}

	public String getGenero() {
		return genero;
	}

	public List<String> getGenerosMusicales() {
		return generosMusicales;
	}

	public List<Cancion> getCanciones() {
		return canciones;
	}

	public List<Artista> getArtistas() {
		return artistas;
	}

	public List<Album> getAlbums() {
		return albums;
	}


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

}