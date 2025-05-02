package sistema;

import modelos.usuario.PerfilUsuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaEmparejamiento {

	public SistemaEmparejamiento() {//Constructor vacio
	}
	//usuario referencia
	public List<PerfilUsuario> referencia(String usuario){
		return GestorArchivos.leerListaObjetos("PerfilUsuario.json", PerfilUsuario.class).stream()
				.filter(perfilUsuario -> perfilUsuario.getUsuario().equals((usuario))).toList();
	}
	//Todos los usuarios excepto el referencia

	public List<PerfilUsuario> general(String usuario){
		return GestorArchivos.leerListaObjetos("PerfilUsuario.json", PerfilUsuario.class).stream()
				.filter(perfilUsuario -> !perfilUsuario.getUsuario().equals((usuario))).toList();
	}
	public Map<String, Integer> porAlbum(String usuario) {
		Map<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (PerfilUsuario perfilUsuario : general(usuario)) {
			int contador = 0;
			for (PerfilUsuario perfilUsuario1 : referencia(usuario)) {
				for (Object o: perfilUsuario.getAlbums()) {
					if (perfilUsuario1.getAlbums().contains(o)) {
						contador+=2;
					}
				}
			}
			puntuacion.put(perfilUsuario.getUsuario(), contador);
		}
		return puntuacion;
	}

	public Map<String, Integer> porArtista(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (PerfilUsuario perfilUsuario : general(usuario)) {
			int contador = 0;
			for (PerfilUsuario perfilUsuario1 : referencia(usuario)) {
				for (Object o : perfilUsuario.getArtistas()) {
					if (perfilUsuario1.getArtistas().contains(o)) {
						contador+=3;
					}
				}
			}
			puntuacion.put(perfilUsuario.getUsuario(), contador);
		}
		return puntuacion;
	}

	public Map<String, Integer> porCancion(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (PerfilUsuario perfilUsuario : general(usuario)) {
			int contador = 0;
			for (PerfilUsuario perfilUsuario1 : referencia(usuario)) {
				for (Object o : perfilUsuario.getCanciones()) {
					if (perfilUsuario1.getCanciones().contains(o)) {
						contador++;
					}
				}
			}
			puntuacion.put(perfilUsuario.getUsuario(), contador);
		}

		return puntuacion;
	}

	public Map<String, Integer> porCarrera(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (PerfilUsuario perfilUsuario : general(usuario)) {
			int contador = 0;
			for (PerfilUsuario perfilUsuario1 : referencia(usuario)) {
				if (perfilUsuario.getCarrera().equals(perfilUsuario1.getCarrera())) {
					contador+=1;
				}
			}
			puntuacion.put(perfilUsuario.getUsuario(), contador);
		}
		return puntuacion;
	}

	public Map<String, Integer> porOrientacion(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (PerfilUsuario perfilUsuario : general(usuario)) {
			int contador = 0;
			for (PerfilUsuario perfilUsuario1 : referencia(usuario)) {
				if (perfilUsuario.getOrientacion().equals(perfilUsuario1.getOrientacion())) {
					contador+=1;
				}
			}
			puntuacion.put(perfilUsuario.getUsuario(), contador);
		}
		return puntuacion;
	}

	public Map<String, Integer> porGenero(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (PerfilUsuario perfilUsuario : general(usuario)) {
			int contador = 0;
			for (PerfilUsuario perfilUsuario1 : referencia(usuario)) {
				if (perfilUsuario.getGenero().equals(perfilUsuario1.getGenero())) {
					contador+=1;
				}
			}
			puntuacion.put(perfilUsuario.getUsuario(), contador);
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