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
    void updateNombreByIdUsuario(String nombre, String idUsuario);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'apellido': ?1}}")
    void updateApellidoByIdUsuario(String apellido, String idUsuario);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'edad': ?1}}")
    void updateEdadByIdUsuario(int edad, String idUsuario);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'imagen': ?1}}")
    void updateImagenByIdUsuario(String imagen, String idUsuario);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'carrera': ?1}}")
    void updateCarreraByIdUsuario(String carrera, String idUsuario);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'orientacion': ?1}}")
    void updateOrientacionByIdUsuario(String orientacion, String idUsuario);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'genero': ?1}}")
    void updateGeneroByIdUsuario(String genero, String idUsuario);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'generoMusicales': ?1}}")
    void updateGeneroMusicalesByIdUsuario(List<String> generoMusicales, String idUsuario);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'canciones': ?1}}")
    void updateCancionesByIdUsuario(List<Cancion> canciones, String idUsuario);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'artistas': ?1}}")
    void updateArtistasByIdUsuario(List<Artista> artistas, String idUsuario);
    @Query("{'idUsuario': ?0}")
    @Update("{'$set': {'albums': ?1}}")
    void updateAlbumByIdUsuario(List<Album> album, String idUsuario);


}
