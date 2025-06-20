package com.banduu.usuario.repositorios.impl;

import com.banduu.usuario.dto.ClienteDTO;
import com.banduu.usuario.repositorios.EmparejamientoRepositorio;
import com.banduu.usuario.servicios.ServicioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Clase que representa el sistema de emparejamiento en la aplicacion.
 * Contiene metodos para comparar usuarios y obtener puntuaciones segun diferentes criterios.
 *
 * @author Vicente Salazar
 * @version 1.0
 */
@Component
public class ImplementacionEmparejamiento implements EmparejamientoRepositorio {

    private ServicioCliente servicioCliente;
	public ImplementacionEmparejamiento() {//Constructor vacio
	}
    @Autowired
    public ImplementacionEmparejamiento(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun el album.
	 *
	 * @param idUsuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun el album
	 */
    @Override
	public Map<String, Integer> porAlbum(String idUsuario) {
        ClienteDTO usuario = servicioCliente.buscarPorId(idUsuario);
		Map<String, Integer> puntuacion = new HashMap<>();
        for (ClienteDTO cliente: servicioCliente.findAll()) {
            if (cliente.id().equals(usuario.id())) {
                continue;
            }
            int contador = 0;
            for (Object o : cliente.albums()) {
                for (Object album : usuario.albums()) {
                    if (album.equals(o)) {
                        contador += 2; // Si el album coincide, se suma 2 puntos
                    }

                }
            }
            puntuacion.put(cliente.id(), contador);
        }
		return puntuacion;
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun el artista.
	 *
	 * @param idUsuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun el artista
	 */
    @Override
    public Map<String, Integer> porArtista(String idUsuario) {
        ClienteDTO usuario = servicioCliente.buscarPorId(idUsuario);
        Map<String, Integer> puntuacion = new HashMap<>();
        for (ClienteDTO cliente: servicioCliente.findAll()) {
            if (cliente.id().equals(usuario.id())) {
                continue;
            }
            int contador = 0;
            for (Object o : cliente.artistas()) {
                for (Object artista : usuario.artistas()) {
                    if (artista.equals(o)) {
                        contador += 3; // Si el artista coincide, se suma 3 puntos
                    }
                }
            }
            puntuacion.put(cliente.id(), contador);
        }
		return puntuacion;
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun la cancion.
	 *
	 * @param idUsuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun la cancion
	 */
    @Override
    public Map<String, Integer> porCancion(String idUsuario) {
        ClienteDTO usuario = servicioCliente.buscarPorId(idUsuario);
        Map<String, Integer> puntuacion = new HashMap<>();
        for (ClienteDTO cliente: servicioCliente.findAll()) {
            if (cliente.id().equals(usuario.id())) {
                continue;
            }
            int contador = 0;
            for (Object o : cliente.canciones()) {
                for (Object cancion : usuario.canciones()) {
                    if (cancion.equals(o)) {
                        contador += 1; // Si la cancion coincide, se suma 1 punto
                    }
                }
            }
            puntuacion.put(cliente.id(), contador);
        }
		return puntuacion;
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun la carrera universitaria.
	 *
	 * @param idUsuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun la carrera universitaria
	 */
    @Override
    public Map<String, Integer> porCarrera(String idUsuario) {
        ClienteDTO usuario = servicioCliente.buscarPorId(idUsuario);
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (ClienteDTO cliente : servicioCliente.findAll()) {
            if (cliente.idUsuario().equals(usuario.id())) {
                continue;
            }
			int contador = 0;
            if (cliente.carrera().equals(usuario.carrera())) {
                contador += 1;
            }
			puntuacion.put(cliente.idUsuario(), contador);
		}
		return puntuacion;
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun la orientacion sexual del usuario.
	 *
	 * @param idUsuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun la orientacion sexual
	 */
    @Override
    public Map<String, Integer> porOrientacion(String idUsuario) {
        ClienteDTO usuario = servicioCliente.buscarPorId(idUsuario);
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (ClienteDTO cliente : servicioCliente.findAll()) {
            if (cliente.idUsuario().equals(usuario.id())) {
                continue;
            }
			int contador = 0;
			if (cliente.orientacion().equals(usuario.orientacion())) {
                contador += 1;
            }
			puntuacion.put(cliente.idUsuario(), contador);
		}
		return puntuacion;
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun el genero biologico.
	 *
	 * @param idUsuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun el genero biologico.
	 */
    @Override
    public Map<String, Integer> porGenero(String idUsuario) {
        ClienteDTO usuario = servicioCliente.buscarPorId(idUsuario);
        HashMap<String, Integer> puntuacion = new HashMap<>();
        //compara todos los usuarios del sistema con el usuario referencia
        for (ClienteDTO cliente : servicioCliente.findAll()) {
            if (cliente.idUsuario().equals(usuario.id())) {
                continue;
            }
            int contador = 0;
            if (cliente.genero().equals(usuario.genero())) {
                contador += 1;
            }
            puntuacion.put(cliente.idUsuario(), contador);
        }
		return puntuacion;
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun el genero musical.
	 *
	 * @param idUsuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun el genero musical.
	 */
    @Override
    public Map<String, Integer> porGenerosMusicales(String idUsuario) {
        ClienteDTO usuario = servicioCliente.buscarPorId(idUsuario);
        HashMap<String, Integer> puntuacion = new HashMap<>();
        for (ClienteDTO cliente : servicioCliente.findAll()) {
            if (cliente.idUsuario().equals(usuario.id())) {
                continue;
            }
            int contador = 0;
            for (String o : cliente.generosMusicales()) {
                if (usuario.generosMusicales().contains(o)) {
                    contador += 1;
                }
            }
            puntuacion.put(cliente.idUsuario(), contador);
        }
        return puntuacion;
	}


	/**
	 *
	 * El metodo marca a los usuarios que tienen la misma carrera universitaria que el usuario ingresado.
	 *
	 * @param idUsuario nombre del usuario con el que se quiere comparar
	 * @return devuelve una lista de usuarios que no tienen la misma carrera universitaria que el usuario ingresado.
	 */
    @Override
    public List<String> descartarMismaCarrera(String idUsuario) {
            List<String> listaUsuarios = new ArrayList<>();
            ClienteDTO usuario = servicioCliente.buscarPorId(idUsuario);
            for (ClienteDTO cliente : servicioCliente.findAll()) {
                if (cliente.carrera().equals(usuario.carrera())) {
                    listaUsuarios.add(cliente.idUsuario());
                }
            }
            return listaUsuarios;
		}

}