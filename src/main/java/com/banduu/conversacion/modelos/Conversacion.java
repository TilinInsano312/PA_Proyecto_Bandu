package com.banduu.conversacion.modelos;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;



@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "conversaciones")
public class  Conversacion {

    private String id;
    private String idEnlace; // Este campo puede ser utilizado para almacenar un enlace a la conversación si es necesario
    private String idRemitente;
    private String idDestinatario;
    private LocalDateTime ultimaActividad;

}
