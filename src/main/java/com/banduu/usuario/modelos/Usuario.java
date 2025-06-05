package com.banduu.usuario.modelos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa un usuario en el sistema.
 * Contiene atributos como usuario, contrasena y email.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */


@Getter
@EqualsAndHashCode
@ToString
@Document(collection = "usuarios")
public class Usuario {

	public Usuario() {
	}

	public Usuario(String id, String nombreUsuario, String contrasena, String email) {
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.email = email;
	}

	@Id
	private String id;

	private String nombreUsuario;
	private String contrasena;
	private String email;

}