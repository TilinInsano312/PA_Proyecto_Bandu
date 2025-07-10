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
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
/**
 * Controlador para manejar los mensajes en la aplicacion.
 * Permite procesar mensajes entrantes y enviar notificaciones a los destinatarios.
 * También proporciona un endpoint para obtener mensajes entre remitentes y destinatarios específicos.
 *
 * @author Sebastian Sandoval
 * @version 1.0
 */
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ControladorMensajes {

    private final ServicioMensaje servicioMensaje;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/mensaje")
    public void procesarMensaje(@Payload Mensaje mensaje) {
       mensaje.setFechaEnvio(LocalDateTime.now());
       mensaje.setLeido(false);

       Mensaje mensajeGuardado = servicioMensaje.save(mensaje);

       simpMessagingTemplate.convertAndSendToUser(
               mensaje.getIdDestinatario(), "/cola/mensajes",
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
