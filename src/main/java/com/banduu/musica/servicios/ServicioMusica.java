package com.banduu.musica.servicios;
import com.banduu.musica.dto.AlbumDTO;
import com.banduu.musica.dto.ArtistaDTO;
import com.banduu.musica.dto.CancionDTO;
import com.banduu.spotify.servicios.SpotifyToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Clase que representa el servicio de musica en el sistema.
 * Contiene metodos para buscar albumes, artistas y canciones en la API de Spotify.
 *
 * @author Vicente Salazar
 * @version 2.0
 */
@Service
public class ServicioMusica {

	private final String accessToken;
	// url
    private final String urlBusqueda = "https://api.spotify.com/v1/search?q=";
	private final String urlartist = "https://api.spotify.com/v1/artists/";

	private final String tipo = "&type=";
	private final String parametro = "&market=ch&limit=1&offset=0&include_external=audio";
	private final HttpClient client;
	private final String textoGenero = "genres";

	public ServicioMusica(SpotifyToken spotifyToken) {
		this.accessToken = "Bearer " + spotifyToken.obtenerAccessToken();
		this.client = HttpClient.newHttpClient();
	}

	/**
	 *
	 * El metodo generaliza la accion de realizar una peticion HTTP a la API de Spotify y devuelve la respuesta.
	 *
	 * @param url url de la peticion
	 * @return devuelve la respuesta de la peticion
	 * @throws IllegalArgumentException si ocurre un error al realizar la peticion
	 * @throws IOException si la peticion es interrumpida
	 */

	private HttpResponse<String> respuestaHTTP(String url){
		HttpRequest peticion = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("Authorization", accessToken)
				.GET()
				.build();
		HttpResponse<String> respuesta;
		try {
			respuesta = client.send(peticion, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalArgumentException(e);
		}
        return respuesta;
	}

	/**
	 *
	 * El metodo busca un album en la API de Spotify y devuelve un objeto Album con la informacion del album.
	 *
	 * @param busqueda nombre del album a buscar
	 * @return devuelve un objeto Album con la informacion del album
	 * @throws IllegalArgumentException si ocurre un error al realizar la peticion
	 */

	public AlbumDTO buscarAlbum(String busqueda) {
		busqueda = busqueda.replace(" ","+");
		String urlConsulta = urlBusqueda +busqueda+ tipo +"album"+ parametro;

		//conexion y lectura del body
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
			root = mapper.readTree(respuestaHTTP(urlConsulta).body());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        JsonNode album = root.at("/albums/items/0");
		if (album.isMissingNode()) {
			return null;
		}

		//instaciar las variables del body
		String albumName = album.get("name").asText();
		String imagenUrl = album.at("/images/0/url").asText();
		JsonNode artista = album.at("/artists/0");
		String artistaName = artista.get("name").asText();
		String artistaId = artista.get("id").asText();

		// Obtener género del artista
        JsonNode artistaRoot;
        try {
            artistaRoot = mapper.readTree(respuestaHTTP(urlartist + artistaId).body());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

        JsonNode generosMusicales = artistaRoot.get(textoGenero);
		String genero = generosMusicales.isArray() && !generosMusicales.isEmpty() ? generosMusicales.get(0).asText() : null;

        return new AlbumDTO(albumName,artistaName,genero,imagenUrl);
    }

	/**
	 *
	 * El metodo busca un artista en la API de Spotify y devuelve un objeto Artista con la informacion del artista.
	 *
	 * @param busqueda nombre del artista a buscar
	 * @return devuelve un objeto Artista con la informacion del artista
	 * @throws IllegalArgumentException si ocurre un error al realizar la peticion
	 */

	public ArtistaDTO buscarArtista(String busqueda) {
		busqueda = busqueda.replace(" ","+");
		//Conexion
		String urlConsulta = urlBusqueda +busqueda+ tipo +"artist"+ parametro;

		//lectura del body
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(respuestaHTTP(urlConsulta).body());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        JsonNode artista = root.at("/artists/items/0");
		if (artista.isMissingNode()) {
			return null;
		}

		//instanciar las variables del body
		String nombreArtista = artista.get("name").asText();
		JsonNode generos = artista.get(textoGenero);
		String imagen = "images";
		String genero = generos.isArray() && !generos.isEmpty() ? String.join(", ", mapper.convertValue(generos, String[].class))
				: null;

		String imagenUrl = artista.has(imagen) && !artista.get(imagen).isEmpty() ? artista.get(imagen).get(0).get("url").asText()
				: null;

		return new ArtistaDTO(nombreArtista,genero,imagenUrl);
	}

	/**
	 *
	 * El metodo busca una cancion en la API de Spotify y devuelve un objeto Cancion con la informacion de la cancion.
	 *
	 * @param busqueda nombre de la cancion a buscar
	 * @return devuelve un objeto Cancion con la informacion de la cancion
	 * @throws IllegalArgumentException si ocurre un error al realizar la peticion
	 */

	public CancionDTO buscarCancion(String busqueda) {
		busqueda = busqueda.replace(" ","+");
		String urlConsulta = urlBusqueda +busqueda+ tipo +"track"+ parametro;
		//lectura del body
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(respuestaHTTP(urlConsulta).body());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        JsonNode track = root.at("/tracks/items/0");
		if (track.isMissingNode()) {
			return null;
		}

		//instanciar variables del body
		String cancionName = track.get("name").asText();
		String imagenUrl = track.at("/album/images/0/url").asText();
		JsonNode artistaNode = track.at("/artists/0");
		String artistaName = artistaNode.get("name").asText();
		String artistaId = artistaNode.get("id").asText();

		//busqueda de los generos
        JsonNode artistRoot;
        try {
            artistRoot = mapper.readTree(respuestaHTTP(urlartist + artistaId).body());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

		//instaciar los generos
        JsonNode generosMusicales = artistRoot.get(textoGenero);
		String genero = generosMusicales.isArray() && !generosMusicales.isEmpty() ? String.join(", ", mapper.convertValue(generosMusicales, String[].class))
				: null;
		return new CancionDTO(cancionName,artistaName,genero,imagenUrl);
	}
}