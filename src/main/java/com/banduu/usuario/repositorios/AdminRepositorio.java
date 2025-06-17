package com.banduu.usuario.repositorios;

import com.banduu.usuario.modelos.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@EnableMongoRepositories
@Repository
public interface AdminRepositorio extends MongoRepository<Admin, String> {

    @Override
    Admin insert(Admin admin);

    @Override
    List<Admin> findAll();

    Optional<Admin> findByIdUsuario(String idUsuario);
}
