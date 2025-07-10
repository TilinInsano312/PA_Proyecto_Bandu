package com.banduu.usuario.dto;
/**
 * Clase que representa un administrador en el sistema.
 * Contiene atributos como id, idUsuario y nombre.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
public record UsuarioDTO(
    String id,
    String nombreUsuario,
    String contrasena,
    String email
) {

}
