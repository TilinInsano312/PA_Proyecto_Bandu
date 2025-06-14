package com.banduu.usuario.repositorios;

import com.banduu.usuario.modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableMongoRepositories
@Repository
public interface UsuarioRepositorio extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

}
