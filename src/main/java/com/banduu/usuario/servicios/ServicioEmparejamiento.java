package com.banduu.usuario.servicios;

import com.banduu.usuario.repositorios.EmparejamientoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * Servicio para gestionar el emparejamiento de usuarios basado en sus preferencias musicales.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
@Service
public class ServicioEmparejamiento {

    private final EmparejamientoRepositorio emparejamientoRepositorio;
    public ServicioEmparejamiento(EmparejamientoRepositorio emparejamientoRepositorio) {
        this.emparejamientoRepositorio = emparejamientoRepositorio;
    }
    public Map<String, Integer> porAlbum(String idUsuario) {
        return emparejamientoRepositorio.porAlbum(idUsuario);
    }
    public Map<String, Integer> porArtista(String idUsuario) {
        return emparejamientoRepositorio.porArtista(idUsuario);
    }
    public Map<String, Integer> porCancion(String idUsuario) {
        return emparejamientoRepositorio.porCancion(idUsuario);
    }
    public Map<String, Integer> porCarrera(String idUsuario) {
        return emparejamientoRepositorio.porCarrera(idUsuario);
    }
    public Map<String, Integer> porOrientacion(String idUsuario) {
        return emparejamientoRepositorio.porOrientacion(idUsuario);
    }
    public Map<String, Integer> porGenero(String idUsuario) {
        return emparejamientoRepositorio.porGenero(idUsuario);
    }
    public Map<String, Integer> porGenerosMusicales(String idUsuario) {
        return emparejamientoRepositorio.porGenerosMusicales(idUsuario);
    }
    public List<String> descartarMismaCarrera(String idUsuario) {
        return emparejamientoRepositorio.descartarMismaCarrera(idUsuario);
    }

}
