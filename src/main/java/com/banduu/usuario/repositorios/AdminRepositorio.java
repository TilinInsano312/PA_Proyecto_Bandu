package com.banduu.usuario.repositorios;

import com.banduu.usuario.modelos.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepositorio extends MongoRepository<Admin, String> {

}
