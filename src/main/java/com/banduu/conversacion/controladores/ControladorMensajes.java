package com.banduu.conversacion.controladores;

import com.banduu.conversacion.modelos.Mensaje;
import com.banduu.conversacion.modelos.NotificacionMensaje;
import com.banduu.conversacion.servicios.ServicioMensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ControladorMensajes {

    private final ServicioMensaje servicioMensaje;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/mensaje")
    public void procesarMensaje(@Payload Mensaje mensaje) {
        Mensaje mensajeGuardado = servicioMensaje.save(mensaje);
        simpMessagingTemplate.convertAndSendToUser(
                mensaje.getIdRemitente(), "/cola/mensajes",
                new NotificacionMensaje(
                        mensajeGuardado.getId(),
                        mensajeGuardado.getIdRemitente(),
                        mensajeGuardado.getIdDestinatario(),
                        mensajeGuardado.getContenido()
                )
        );

    }




    @GetMapping("/mensajes/{idRemitente}/{idDestinatario}")
    public ResponseEntity<List<Mensaje>> obtenerMensajes(@PathVariable String idRemitente, @PathVariable String idDestinatario) {
        return ResponseEntity.ok(servicioMensaje.obtenerMensajes(idRemitente, idDestinatario));
    }
}
