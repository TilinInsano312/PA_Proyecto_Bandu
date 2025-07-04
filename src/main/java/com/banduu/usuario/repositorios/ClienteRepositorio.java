package com.banduu.usuario.repositorios;

import com.banduu.musica.modelos.Album;
import com.banduu.musica.modelos.Artista;
import com.banduu.musica.modelos.Cancion;
import com.banduu.usuario.modelos.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableMongoRepositories
@Repository
public interface ClienteRepositorio extends MongoRepository<Cliente, String> {
    Cliente insert(Cliente cliente);


    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'nombre': ?1}}")
    void updateNombreByIdUsuario(String idUsuario,String nombre );
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'apellido': ?1}}")
    void updateApellidoByIdUsuario(String idUsuario,String apellido);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'edad': ?1}}")
    void updateEdadByIdUsuario(String idUsuario,int edad );
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'imagen': ?1}}")
    void updateImagenByIdUsuario(String idUsuario,String imagen );
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'carrera': ?1}}")
    void updateCarreraByIdUsuario(String idUsuario, String carrera );
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'orientacion': ?1}}")
    void updateOrientacionByIdUsuario(String idUsuario, String orientacion);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'genero': ?1}}")
    void updateGeneroByIdUsuario(String idUsuario, String genero);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'generoMusicales': ?1}}")
    void updateGeneroMusicalesByIdUsuario(String idUsuario, List<String> generoMusicales);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'canciones': ?1}}")
    void updateCancionesByIdUsuario(String idUsuario, List<Cancion> canciones);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'artistas': ?1}}")
    void updateArtistasByIdUsuario(String idUsuario, List<Artista> artistas);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'albums': ?1}}")
    void updateAlbumByIdUsuario(String idUsuario, List<Album> album);


}
