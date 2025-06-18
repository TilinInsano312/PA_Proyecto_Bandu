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
      return repositorioConversacion.findByIdRemitenteAndIdDestinatario(idUsuario1, idUsuario2)
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
        var conversacionId = String.format("%s-%s", idUsuario1, idUsuario2);
        Conversacion remitenteDestinatario = Conversacion.builder()
                .id(conversacionId)
                .idRemitente(idUsuario1)
                .idDestinatario(idUsuario2)
                .build();

        Conversacion destinatarioRemitente = Conversacion.builder()
                .id(conversacionId)
                .idRemitente(idUsuario2)
                .idDestinatario(idUsuario1)
                .build();

        repositorioConversacion.save(remitenteDestinatario);
        repositorioConversacion.save(destinatarioRemitente);
        return conversacionId;

    }


}
