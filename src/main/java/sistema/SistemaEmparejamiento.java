package sistema;

import modelos.usuario.Cliente;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaEmparejamiento {

	public SistemaEmparejamiento() {//Constructor vacio
	}
	//usuario referencia
	public List<Cliente> referencia(String usuario){
		return GestorArchivos.leerListaObjetos("PerfilUsuario.json", Cliente.class).stream()
				.filter(perfilUsuario -> perfilUsuario.getUsuario().equals((usuario))).toList();
	}
	//Todos los usuarios excepto el referencia

	public List<Cliente> general(String usuario){
		return GestorArchivos.leerListaObjetos("PerfilUsuario.json", Cliente.class).stream()
				.filter(perfilUsuario -> !perfilUsuario.getUsuario().equals((usuario))).toList();
	}
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
	//lee el hashmap de porCarrera() y enlista a todos los nombre de usuario que tienen hashvalor 1
		public List<String> descartarMismaCarrera(String usuario) {
			return porCarrera(usuario).entrySet().stream()
					.filter(entry -> entry.getValue() == 1)
					.map(Map.Entry::getKey)
					.toList();
		}

}