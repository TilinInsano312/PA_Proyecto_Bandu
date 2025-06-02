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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
@Document(collection = "clientes")
public class Cliente extends Usuario {

	@Id
	private String id;

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