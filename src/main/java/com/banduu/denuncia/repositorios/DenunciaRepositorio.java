package com.banduu.denuncia.repositorios;

import com.banduu.denuncia.modelos.Denuncia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@EnableMongoRepositories
@Repository
public interface DenunciaRepositorio extends MongoRepository<Denuncia, String> {
    Denuncia save(Denuncia denuncia);
}
