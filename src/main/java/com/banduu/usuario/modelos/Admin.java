package com.banduu.usuario.modelos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa un administrador en el sistema.
 * Hereda de la clase Usuario y contiene un atributo adicional: nombre.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */

@ToString
@Document(collection = "administradores")
public class Admin {


	public Admin() {
	}

	public Admin(String id, String idUsuario, String nombre) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.nombre = nombre;
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

	@Id
	private String id;

	@Indexed(unique = true)
	private String idUsuario;
	private String nombre;

}