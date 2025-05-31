package com.banduu.usuario.modelos;

import lombok.*;

/**
 * Clase que representa un usuario en el sistema.
 * Contiene atributos como usuario, contrasena y email.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Usuario {

	private String usuario;
	private String contrasena;
	private String email;

}