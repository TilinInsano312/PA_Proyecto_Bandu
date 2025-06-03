package com.banduu.musica.repositorios;

import com.banduu.musica.modelos.Album;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlbumRepositorio extends MongoRepository<Album, String> {


}
