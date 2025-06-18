package com.banduu.conversacion.repositorios;

import com.banduu.conversacion.modelos.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioMensaje extends MongoRepository<Mensaje, String> {


    List<Mensaje> findByIdEnlace(String idEnlace);
}
