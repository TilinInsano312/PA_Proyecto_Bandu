package com.banduu.conversacion.repositorios;

import com.banduu.conversacion.modelos.Conversacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface RepositorioConversacion extends MongoRepository<Conversacion, String> {

    Optional<Conversacion> findByIdRemitenteAndIdDestinatario(String idRemitente, String idDestinatario);
}
