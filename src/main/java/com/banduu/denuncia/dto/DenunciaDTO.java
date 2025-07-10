package com.banduu.denuncia.dto;

import com.banduu.usuario.modelos.Cliente;
/**
 * Clase que representa una denuncia en el sistema.
 * Contiene atributos como el id de la denuncia, el perfil denunciado, la descripcion de la denuncia y el tipo de denuncia.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
public record DenunciaDTO(String id, Cliente perfilDenunciado, String descripcion, String tipoDenuncia) {

}
