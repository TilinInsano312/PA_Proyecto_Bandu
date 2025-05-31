package com.banduu.usuario.modelos;

import lombok.*;

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
public class Admin extends Usuario {

	private String nombre;

}