package com.banduu.denuncia.repositorios;

import com.banduu.denuncia.modelos.Denuncia;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DenunciaRepositorio extends MongoRepository<Denuncia, String> {

}
