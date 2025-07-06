package com.banduu.conversacion.servicios;

import com.banduu.conversacion.modelos.Mensaje;
import com.banduu.conversacion.repositorios.RepositorioMensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Servicio que gestiona las operaciones relacionadas con mensajes.
 * Proporciona funcionalidades para guardar mensajes y recuperar historial de conversaciones.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ServicioMensaje {

    private final RepositorioMensaje repositorioMensaje;
    private final ServicioConversacion servicioConversacion;

    /**
     * Guarda un mensaje en la base de datos asociandolo a una conversacion.
     * Si no existe una conversacion entre los participantes, la crea automaticamente.
     *
     * @param mensaje El mensaje a guardar, debe contener idRemitente e idDestinatario
     * @return El mensaje guardado con el ID de conversacion asignado
     */
    public Mensaje save(Mensaje mensaje) {
        var idConversacion = servicioConversacion.obtenerIdConversacion(
                mensaje.getIdRemitente(),
                mensaje.getIdDestinatario(),
                true
        ).orElseThrow(() -> new RuntimeException("No se pudo obtener el ID de la conversación"));
        mensaje.setConversacionId(idConversacion);
        repositorioMensaje.save(mensaje);
        return mensaje;
    }


    public List<Mensaje> obtenerMensajes(String idRemitente, String idDestinatario) {
        var conversacionId = servicioConversacion.obtenerIdConversacion(idRemitente, idDestinatario, false);
        return conversacionId.map(repositorioMensaje::findByConversacionId).orElse(new ArrayList<>());
    }

}
