package com.banduu.usuario.modelos;

import com.banduu.musica.modelos.Album;
import com.banduu.musica.modelos.Artista;
import com.banduu.musica.modelos.Cancion;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un cliente en el sistema.
 * Hereda de la clase Usuario y contiene atributos adicionales como nombre, apellido, edad, carrera, etc.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 2.0
 */

@ToString
@Document(collection = "clientes")
public class Cliente {

	public Cliente() {
	}
	public Cliente(String id, String idUsuario, String nombre, String apellido, int edad, String carrera,
	                String orientacion, String genero, List<String> generosMusicales,
	               List<Cancion> canciones, List<Artista> artistas, List<Album> albums) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.carrera = carrera;
		this.orientacion = orientacion;
		this.genero = genero;
		this.generosMusicales = generosMusicales;
		this.canciones = canciones;
		this.artistas = artistas;
		this.albums = albums;
	}

	public String getId() {
		return id;
	}
	public String getIdUsuario() {
		return idUsuario;
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


	@Id
	private String id;

	@Indexed(unique = true)
	private String idUsuario;

	private String nombre;
	private String apellido;
	private int edad;
	private String carrera;
	private String orientacion;
	private String genero;
	private List<String> generosMusicales;
	private List<Cancion> canciones;
	private List<Artista> artistas;
	private List<Album> albums;

}