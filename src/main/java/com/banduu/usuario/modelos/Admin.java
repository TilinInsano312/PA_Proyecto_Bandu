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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
@Document(collection = "administradores")
public class Admin extends Usuario {

	@Id
	private String id;

	private String nombre;

}