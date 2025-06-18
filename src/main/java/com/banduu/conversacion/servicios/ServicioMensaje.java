package com.banduu.conversacion.servicios;

import com.banduu.conversacion.modelos.Mensaje;
import com.banduu.conversacion.repositorios.RepositorioMensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioMensaje {

    private final RepositorioMensaje repositorioMensaje;
    private final ServicioConversacion servicioConversacion;

    public Mensaje save(Mensaje mensaje) {
        var idConversacion = servicioConversacion.obtenerIdConversacion(
                mensaje.getIdRemitente(),
                mensaje.getIdDestinatario(),
                true
        ).orElseThrow(() -> new RuntimeException("No se pudo obtener el ID de la conversación"));
        mensaje.setIdEnlace(idConversacion);
        repositorioMensaje.save(mensaje);
        return mensaje;
    }

    public List<Mensaje> obtenerMensajes(String idRemitente, String idDestinatario) {
        var conversacionId = servicioConversacion.obtenerIdConversacion(idRemitente, idDestinatario, false);
        return conversacionId.map(repositorioMensaje::findByIdEnlace).orElse(new ArrayList<>());
    }

}
