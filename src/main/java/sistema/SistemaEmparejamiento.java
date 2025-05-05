package sistema;

import modelos.usuario.Cliente;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa el sistema de emparejamiento en la aplicacion.
 * Contiene metodos para comparar usuarios y obtener puntuaciones segun diferentes criterios.
 *
 * @author Vicente Salazar
 * @version 1.0
 */
public class SistemaEmparejamiento {

	public SistemaEmparejamiento() {//Constructor vacio
	}

	/**
	 *
	 * El metodo toma un usuario y lo retorna en una lista de objetos de tipo Cliente.
	 *
	 * @param usuario nombre del usuario con el que se quiere comparar
	 * @return Lista de usuarios que tienen el mismo usuario que el usuario referencia
	 */
	public List<Cliente> referencia(String usuario){
		return GestorArchivos.leerListaObjetos("Cliente.json", Cliente.class).stream()
				.filter(perfilUsuario -> perfilUsuario.getUsuario().equals((usuario))).toList();
	}

	/**
	 *
	 * El metodo toma un usuario y lo retorna en una lista de objetos de tipo Cliente cuales son todos los clientes existentes excepto el usuario del parametro.
	 *
	 * @param usuario nombre del usuario con el que se quiere comparar
	 * @return Lista de Cliente que no tienen el mismo usuario que el parametro.
	 */
	public List<Cliente> general(String usuario){
		return GestorArchivos.leerListaObjetos("Cliente.json", Cliente.class).stream()
				.filter(perfilUsuario -> !perfilUsuario.getUsuario().equals((usuario))).toList();
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun el album.
	 *
	 * @param usuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun el album
	 */
	public Map<String, Integer> porAlbum(String usuario) {
		Map<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Cliente cliente : general(usuario)) {
			int contador = 0;
			for (Cliente cliente1 : referencia(usuario)) {
				for (Object o: cliente.getAlbums()) {
					if (cliente1.getAlbums().contains(o)) {
						contador+=2;
					}
				}
			}
			puntuacion.put(cliente.getUsuario(), contador);
		}
		return puntuacion;
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun el artista.
	 *
	 * @param usuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun el artista
	 */
	public Map<String, Integer> porArtista(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Cliente cliente : general(usuario)) {
			int contador = 0;
			for (Cliente cliente1 : referencia(usuario)) {
				for (Object o : cliente.getArtistas()) {
					if (cliente1.getArtistas().contains(o)) {
						contador+=3;
					}
				}
			}
			puntuacion.put(cliente.getUsuario(), contador);
		}
		return puntuacion;
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun la cancion.
	 *
	 * @param usuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun la cancion
	 */
	public Map<String, Integer> porCancion(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Cliente cliente : general(usuario)) {
			int contador = 0;
			for (Cliente cliente1 : referencia(usuario)) {
				for (Object o : cliente.getCanciones()) {
					if (cliente1.getCanciones().contains(o)) {
						contador++;
					}
				}
			}
			puntuacion.put(cliente.getUsuario(), contador);
		}

		return puntuacion;
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun la carrera universitaria.
	 *
	 * @param usuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun la carrera universitaria
	 */
	public Map<String, Integer> porCarrera(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Cliente cliente : general(usuario)) {
			int contador = 0;
			for (Cliente cliente1 : referencia(usuario)) {
				if (cliente.getCarrera().equals(cliente1.getCarrera())) {
					contador+=1;
				}
			}
			puntuacion.put(cliente.getUsuario(), contador);
		}
		return puntuacion;
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun la orientacion sexual del usuario.
	 *
	 * @param usuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun la orientacion sexual
	 */
	public Map<String, Integer> porOrientacion(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Cliente cliente : general(usuario)) {
			int contador = 0;
			for (Cliente cliente1 : referencia(usuario)) {
				if (cliente.getOrientacion().equals(cliente1.getOrientacion())) {
					contador+=1;
				}
			}
			puntuacion.put(cliente.getUsuario(), contador);
		}
		return puntuacion;
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun el genero biologico.
	 *
	 * @param usuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun el genero biologico.
	 */
	public Map<String, Integer> porGenero(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Cliente cliente : general(usuario)) {
			int contador = 0;
			for (Cliente cliente1 : referencia(usuario)) {
				if (cliente.getGenero().equals(cliente1.getGenero())) {
					contador+=1;
				}
			}
			puntuacion.put(cliente.getUsuario(), contador);
		}
		return puntuacion;
	}

	/**
	 *
	 * El metodo compara el usuario de referencia con todos los usuarios del sistema y devuelve un mapa con la puntuacion de cada usuario segun el genero musical.
	 *
	 * @param usuario nombre del usuario con el que se quiere comparar
	 * @return devuelve un mapa con todos los usuarios que no son el usuario ingresado en el parametro y la puntuacion de cada usuario segun el genero musical.
	 */
	public Map<String, Integer> porGenerosMusicales(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Cliente cliente : general(usuario)) {
			int contador = 0;
			for (Cliente cliente1 : referencia(usuario)) {
				for (String o : cliente.getGenerosMusicales()) {
					if (cliente1.getGenerosMusicales().contains(o)) {
						contador+=1;
					}
				}
			}
			puntuacion.put(cliente.getUsuario(), contador);
		}
		return puntuacion;
	}


	/**
	 *
	 * El metodo marca a los usuarios que tienen la misma carrera universitaria que el usuario ingresado.
	 *
	 * @param usuario nombre del usuario con el que se quiere comparar
	 * @return devuelve una lista de usuarios que no tienen la misma carrera universitaria que el usuario ingresado.
	 */
		public List<String> descartarMismaCarrera(String usuario) {
			return porCarrera(usuario).entrySet().stream()
					.filter(entry -> entry.getValue() == 1)
					.map(Map.Entry::getKey)
					.toList();
		}

}