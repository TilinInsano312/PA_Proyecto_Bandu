package com.banduu.musica.repositorios;

import com.banduu.musica.modelos.Artista;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtistaRepositorio extends MongoRepository<Artista, String> {

}
