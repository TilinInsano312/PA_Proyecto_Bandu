package com.banduu.conversacion.modelos;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
/**
 * Clase que representa una conversacion entre dos usuarios en el sistema.
 * Gestiona la relacion entre mensajes y participantes de la conversacion.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "conversaciones")
public class  Conversacion {

    private String id;
    private String idRemitente;
    private String idDestinatario;
    private LocalDateTime ultimaActividad;

}
