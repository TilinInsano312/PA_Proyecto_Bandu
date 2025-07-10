package com.banduu.conversacion.dto;

import java.util.Date;
/**
 * Clase que representa un mensaje en una conversacion.
 * Contiene atributos como el id del mensaje, emisor, receptor, contenido, fecha de envío, estado de lectura y el id de la conversacion.
 *
 * @author Sebastian Sandoval
 * @version 1.0
 */
public record MensajeDTO(
        String id,
        String emisor,
        String receptor,
        String contenido,
        Date fechaEnvio,
        boolean leido,
        String conversacionId) {
}
