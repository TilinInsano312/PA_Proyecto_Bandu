package com.banduu.usuario.repositorios;

import com.banduu.usuario.modelos.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@EnableMongoRepositories
@Repository
public interface ClienteRepositorio extends MongoRepository<Cliente, String> {
    Cliente insert(Cliente cliente);


}
