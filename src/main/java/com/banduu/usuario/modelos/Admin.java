package com.banduu.usuario.modelos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa un administrador en el sistema.
 * Hereda de la clase Usuario y contiene un atributo adicional: nombre.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */

@EqualsAndHashCode(callSuper = true)
@ToString
@Document(collection = "administradores")
public class Admin extends Usuario {

	public Admin() {
	}
	public Admin(String nombre) {
		this.nombre = nombre;
	}

	public Admin(String id, String nombreUsuario, String contrasena, String email, String nombre) {
		super(id, nombreUsuario, contrasena, email);
		this.nombre = nombre;
	}


	public String getNombre() {
		return nombre;
	}


	private String nombre;

}