package com.banduu.conversacion.modelos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
/**
 * Clase que representa un mensaje en el sistema de conversaciones.
 * Contiene informacion del remitente, destinatario y contenido.
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
@Document(collection = "mensajes")
public class Mensaje {

    @Id
    private String id;
    private String idRemitente;
    private String idDestinatario;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private boolean leido;
    private String conversacionId;

}
