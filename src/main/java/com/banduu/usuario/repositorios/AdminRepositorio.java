package com.banduu.usuario.repositorios;

import com.banduu.usuario.modelos.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableMongoRepositories
@Repository
public interface AdminRepositorio extends MongoRepository<Admin, String> {
    Admin insert(Admin admin);
    List<Admin> findAll();
}
