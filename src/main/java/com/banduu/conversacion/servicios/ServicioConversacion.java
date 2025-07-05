package com.banduu.conversacion.servicios;

import com.banduu.conversacion.modelos.Conversacion;
import com.banduu.conversacion.repositorios.RepositorioConversacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ServicioConversacion {

    private final RepositorioConversacion repositorioConversacion;

    public Optional<String> obtenerIdConversacion(String idUsuario1, String idUsuario2, boolean crearSiNoExiste) {
      return repositorioConversacion.findByParticipantes(idUsuario1, idUsuario2)
              .map(Conversacion::getId)
              .or(() -> {
                  if (crearSiNoExiste){
                    var idConversacion = crearIdConversacion(idUsuario1, idUsuario2);
                    return Optional.of(idConversacion);
                  }
                  return Optional.empty();
              });
    }

    private String crearIdConversacion(String idUsuario1, String idUsuario2) {
        String conversacionId = idUsuario1.compareTo(idUsuario2) < 0
                ? String.format("%s-%s", idUsuario1, idUsuario2)
                : String.format("%s-%s", idUsuario2, idUsuario1);

        Conversacion conversacion = Conversacion.builder()
                .id(conversacionId)
                .idRemitente(idUsuario1)
                .idDestinatario(idUsuario2)
                .ultimaActividad(java.time.LocalDateTime.now())
                .build();

        repositorioConversacion.save(conversacion);
        return conversacionId;

    }


}
