package com.banduu.conversacion.dto;

import java.util.Date;

public record MensajeDTO(
        String id,
        String emisor,
        String receptor,
        String contenido,
        Date fechaEnvio,
        boolean leido,
        String conversacionId) {
}
