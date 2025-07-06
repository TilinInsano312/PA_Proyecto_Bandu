package com.banduu.conversacion.modelos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacionMensaje {

    private String id;
    private String idRemitente;
    private String idDestinatario;
    private String contenido;

}
