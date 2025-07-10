package com.banduu.conversacion.modelos;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacionMensaje {

    @Id
    private String id;
    private String idRemitente;
    private String idDestinatario;
    private String contenido;

}
