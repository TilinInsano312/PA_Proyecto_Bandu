package com.banduu.usuario.dto;
/**
 * Clase que representa un administrador en el sistema.
 * Contiene atributos como id, idUsuario y nombre.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
public record AdminDTO(
        String id,
        String idUsuario,
        String nombre) {
}
