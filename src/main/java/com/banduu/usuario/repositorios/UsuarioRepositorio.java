package com.banduu.usuario.repositorios;


import com.banduu.usuario.modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import org.springframework.stereotype.Repository;


import java.util.Optional;

@EnableMongoRepositories
@Repository
public interface UsuarioRepositorio extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    @Query("{'id': ?0}")
    @Update("{'$set': {'contrasena': ?1}}")
    void updateContrasenaById(String id,String contrasena );
    @Query("{'id': ?0}")
    @Update("{'$set': {'email': ?1}}")
    void updateEmailById( String id, String email);
}
