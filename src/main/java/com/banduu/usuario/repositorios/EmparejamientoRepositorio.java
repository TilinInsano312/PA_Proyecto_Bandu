package com.banduu.usuario.repositorios;


import java.util.List;
import java.util.Map;


public interface EmparejamientoRepositorio {
    Map<String, Integer> porAlbum(String idUsuario);
    Map<String, Integer> porArtista(String idUsuario);
    Map<String, Integer> porCancion(String idUsuario);
    Map<String, Integer> porCarrera(String idUsuario);
    Map<String, Integer> porOrientacion(String idUsuario);
    Map<String, Integer> porGenero(String idUsuario);
    Map<String, Integer> porGenerosMusicales(String idUsuario);
    List<String> descartarMismaCarrera(String idUsuario);
}
