package com.banduu.musica.repositorios;

import com.banduu.musica.modelos.Cancion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CancionRepositorio extends MongoRepository<Cancion, String> {

}
