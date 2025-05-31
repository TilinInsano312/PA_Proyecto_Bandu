package com.banduu.usuario.modelos;

import com.banduu.musica.modelos.Album;
import com.banduu.musica.modelos.Artista;
import com.banduu.musica.modelos.Cancion;
import lombok.*;

import java.util.List;

/**
 * Clase que representa un cliente en el sistema.
 * Hereda de la clase Usuario y contiene atributos adicionales como nombre, apellido, edad, carrera, etc.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 2.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Cliente extends Usuario {

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