package com.banduu.usuario.repositorios;

import com.banduu.usuario.modelos.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepositorio extends MongoRepository<Cliente, String> {

    Optional<Cliente> findByNombre(String nombreUsuario);

    void deleteByNombre(String nombre);

    void updateNombre(String id, String nuevoNombre);

    void updateApellido(String id, String nuevoApellido);

    void updateEdad(String id, int nuevaEdad);

    void updateCarrera(String id, String nuevaCarrera);

    void updateMismaCarrera(String id, boolean nuevaMisimaCarrera);

    void updateOrientacion(String id, String nuevaOrientacion);

    void updateGenero(String id, String nuevoGenero);

    void updateGenerosMusicales(String id, List<String> nuevosGenerosMusicales);

    void updateCanciones(String id, List<String> nuevasCanciones);

    void updateArtistas(String id, List<String> nuevosArtistas);

    void updateAlbums(String id, List<String> nuevosAlbums);
}
