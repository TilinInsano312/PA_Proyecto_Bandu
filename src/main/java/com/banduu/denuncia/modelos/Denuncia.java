package com.banduu.denuncia.modelos;

import com.banduu.usuario.modelos.Cliente;

import lombok.*;

/**
 * Clase que representa una denuncia en el sistema.
 * Contiene atributos como el perfil denunciado, la descripcion de la denuncia y el tipo de denuncia.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Denuncia {

	private Cliente perfilDenunciado;
	private String descripcion;
	private String tipoDenuncia;

}