package com.banduu.usuario.repositorios;

import com.banduu.usuario.modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends MongoRepository<Usuario, String> {

    List<Usuario> findAllByNombreUsuario(String nombreUsuario);

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    void updateContrasena(String id, String nuevaContrasena);



}
