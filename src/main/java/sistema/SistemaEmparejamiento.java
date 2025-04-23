package sistema;

import modelos.usuario.PerfilUsuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SistemaEmparejamiento {
	public SistemaEmparejamiento() {
	}
	//usuario referencia
	public List referencia(String usuario){
		return GestorArchivos.leerListaObjetos("PerfilUsuario.json", PerfilUsuario.class).stream()
				.filter(perfilUsuario -> perfilUsuario.getUsuario().equals((usuario))).toList();
	}
	//Todos los usuarios excepto el referencia

	public List general(String usuario){
		return GestorArchivos.leerListaObjetos("PerfilUsuario.json", PerfilUsuario.class).stream()
				.filter(perfilUsuario -> !perfilUsuario.getUsuario().equals((usuario))).toList();
	}
	public HashMap<String, Integer> porAlbum(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Object o : general(usuario)) {
			PerfilUsuario perfilUsuario = (PerfilUsuario) o;
			int contador = 0;
			for (Object o1 : referencia(usuario)) {
				PerfilUsuario perfilUsuario1 = (PerfilUsuario) o1;
				for (Object o2 : perfilUsuario.getAlbums()) {
					if (perfilUsuario1.getAlbums().contains(o2)) {
						contador+=2;
					}
				}
			}
			puntuacion.put(perfilUsuario.getUsuario(), contador);
		}

		return puntuacion;
	}

	public HashMap<String, Integer> porArtista(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Object o : general(usuario)) {
			PerfilUsuario perfilUsuario = (PerfilUsuario) o;
			int contador = 0;
			for (Object o1 : referencia(usuario)) {
				PerfilUsuario perfilUsuario1 = (PerfilUsuario) o1;
				for (Object o2 : perfilUsuario.getArtistas()) {
					if (perfilUsuario1.getArtistas().contains(o2)) {
						contador+=3;
					}
				}
			}
			puntuacion.put(perfilUsuario.getUsuario(), contador);
		}

		return puntuacion;
	}

	public HashMap<String, Integer> porCancion(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Object o : general(usuario)) {
			PerfilUsuario perfilUsuario = (PerfilUsuario) o;
			int contador = 0;
			for (Object o1 : referencia(usuario)) {
				PerfilUsuario perfilUsuario1 = (PerfilUsuario) o1;
				for (Object o2 : perfilUsuario.getCanciones()) {
					if (perfilUsuario1.getCanciones().contains(o2)) {
						contador++;
					}
				}
			}
			puntuacion.put(perfilUsuario.getUsuario(), contador);
		}

		return puntuacion;
	}

	public HashMap<String, Integer> porCarrera(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Object o : general(usuario)) {
			PerfilUsuario perfilUsuario = (PerfilUsuario) o;
			int contador = 0;
			for (Object o1 : referencia(usuario)) {
				PerfilUsuario perfilUsuario1 = (PerfilUsuario) o1;
				if (perfilUsuario.getCarrera().equals(perfilUsuario1.getCarrera())) {
					contador+=1;
				}
			}
			puntuacion.put(perfilUsuario.getUsuario(), contador);
		}
		System.out.println(puntuacion);
		return puntuacion;
	}

	public HashMap<String, Integer> porOrientacion(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Object o : general(usuario)) {
			PerfilUsuario perfilUsuario = (PerfilUsuario) o;
			int contador = 0;
			for (Object o1 : referencia(usuario)) {
				PerfilUsuario perfilUsuario1 = (PerfilUsuario) o1;
				if (perfilUsuario.getOrientacion().equals(perfilUsuario1.getOrientacion())) {
					contador+=1;
				}
			}
			puntuacion.put(perfilUsuario.getUsuario(), contador);
		}
		System.out.println(puntuacion);
		return puntuacion;
	}

	public HashMap<String, Integer> porGenero(String usuario) {
		HashMap<String, Integer> puntuacion = new HashMap<>();
		//compara todos los usuarios del sistema con el usuario referencia
		for (Object o : general(usuario)) {
			PerfilUsuario perfilUsuario = (PerfilUsuario) o;
			int contador = 0;
			for (Object o1 : referencia(usuario)) {
				PerfilUsuario perfilUsuario1 = (PerfilUsuario) o1;
				if (perfilUsuario.getGenero().equals(perfilUsuario1.getGenero())) {
					contador+=1;
				}
			}
			puntuacion.put(perfilUsuario.getUsuario(), contador);
		}
		System.out.println(puntuacion);
		return puntuacion;
	}
	//lee el hashmap de porCarrera() y enlista a todos los nombre de usuario que tienen hashvalor 1
		public List descartarMismaCarrera(String usuario) {
			return porCarrera(usuario).entrySet().stream()
					.filter(entry -> entry.getValue() == 1)
					.map(Map.Entry::getKey)
					.collect(Collectors.toList());
		}

}