package com.banduu.conversacion.modelos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;



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
    private String idEnlace;
    private String idRemitente;
    private String idDestinatario;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private boolean leido;
    private String conversacionId;

}
