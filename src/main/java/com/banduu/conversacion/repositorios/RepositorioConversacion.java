package com.banduu.conversacion.repositorios;

import com.banduu.conversacion.modelos.Conversacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RepositorioConversacion extends MongoRepository<Conversacion, String> {

    @Query("{ $or: [ " +
            "{ 'idRemitente': ?0, 'idDestinatario': ?1 }, " +
            "{ 'idRemitente': ?1, 'idDestinatario': ?0 } " +
            "] }")
    Optional<Conversacion> findByParticipantes(String usuario1, String usuario2);
}
