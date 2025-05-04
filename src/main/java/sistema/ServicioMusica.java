package sistema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import modelos.musica.Album;
import modelos.musica.Artista;
import modelos.musica.Cancion;

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

public class ServicioMusica {
	private static final String ACCESS_TOKEN;
    static {
        try {
            ACCESS_TOKEN = "Bearer "+SpotifyToken.obtenerAccessToken();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
	// url
    private static final String URLBUSQUEDA= "https://api.spotify.com/v1/search?q=";
	private static final String URLARTIST= "https://api.spotify.com/v1/artists/";

	private static final String TIPO = "&type=";
	private static final String PARAMETRO = "&market=ch&limit=1&offset=0&include_external=audio";
	private static final HttpClient client = HttpClient.newHttpClient();
	private static final String TEXTOGENERO = "genres";

	/**
	 *
	 * El metodo generaliza la accion de realizar una peticion HTTP a la API de Spotify y devuelve la respuesta.
	 *
	 * @param url url de la peticion
	 * @return devuelve la respuesta de la peticion
	 * @throws IllegalArgumentException si ocurre un error al realizar la peticion
	 * @throws IOException si la peticion es interrumpida
	 */

	public HttpResponse<String> respuestaHTTP(String url){
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("Authorization", ACCESS_TOKEN)
				.GET()
				.build();
		HttpResponse<String> response;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalArgumentException(e);
		}
        return response;
	}

	/**
	 *
	 * El metodo busca un album en la API de Spotify y devuelve un objeto Album con la informacion del album.
	 *
	 * @param busqueda nombre del album a buscar
	 * @return devuelve un objeto Album con la informacion del album
	 * @throws IllegalArgumentException si ocurre un error al realizar la peticion
	 */

	public Album buscarAlbum(String busqueda) {
		busqueda = busqueda.replace(" ","+");
		String urlConsulta = URLBUSQUEDA+busqueda+TIPO+"album"+PARAMETRO;

		//conexion y lectura del body
        ObjectMapper mapper = new ObjectMapper();
        JsonNode raiz;
        try {
			raiz = mapper.readTree(respuestaHTTP(urlConsulta).body());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        JsonNode album = raiz.at("/albums/items/0");
		if (album.isMissingNode()) {
			return null;
		}

		//instaciar las variables del body
		String albumName = album.get("name").asText();
		String imageUrl = album.at("/images/0/url").asText();
		JsonNode artist = album.at("/artists/0");
		String artistName = artist.get("name").asText();
		String artistId = artist.get("id").asText();

		// Obtener género del artista
        JsonNode artistRoot;
        try {
            artistRoot = mapper.readTree(respuestaHTTP(URLARTIST + artistId).body());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

        JsonNode genres = artistRoot.get(TEXTOGENERO);
		String genre = genres.isArray() && !genres.isEmpty() ? genres.get(0).asText() : null;

        return new Album(albumName,artistName,genre,imageUrl);
    }

	/**
	 *
	 * El metodo busca un artista en la API de Spotify y devuelve un objeto Artista con la informacion del artista.
	 *
	 * @param busqueda nombre del artista a buscar
	 * @return devuelve un objeto Artista con la informacion del artista
	 * @throws IllegalArgumentException si ocurre un error al realizar la peticion
	 */

	public Artista buscarArtista(String busqueda) {
		busqueda = busqueda.replace(" ","+");
		//Conexion
		String urlConsulta = URLBUSQUEDA+busqueda+TIPO+"artist"+PARAMETRO;

		//lectura del body
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(respuestaHTTP(urlConsulta).body());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        JsonNode artist = root.at("/artists/items/0");
		if (artist.isMissingNode()) {
			return null;
		}

		//instanciar las variables del body
		String nombreArtista = artist.get("name").asText();
		JsonNode generos = artist.get(TEXTOGENERO);
		String images = "images";
		String genero = generos.isArray() && !generos.isEmpty() ? String.join(", ", mapper.convertValue(generos, String[].class))
				: null;

		String imagenUrl = artist.has(images) && !artist.get(images).isEmpty() ? artist.get(images).get(0).get("url").asText()
				: null;

		return new Artista(nombreArtista,genero,imagenUrl);
	}

	/**
	 *
	 * El metodo busca una cancion en la API de Spotify y devuelve un objeto Cancion con la informacion de la cancion.
	 *
	 * @param busqueda nombre de la cancion a buscar
	 * @return devuelve un objeto Cancion con la informacion de la cancion
	 * @throws IllegalArgumentException si ocurre un error al realizar la peticion
	 */

	public Cancion buscarCancion(String busqueda) {
		busqueda = busqueda.replace(" ","+");
		String urlConsulta = URLBUSQUEDA+busqueda+TIPO+"track"+PARAMETRO;
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
		String songName = track.get("name").asText();
		String imageUrl = track.at("/album/images/0/url").asText();
		JsonNode artistNode = track.at("/artists/0");
		String artistName = artistNode.get("name").asText();
		String artistId = artistNode.get("id").asText();

		//busqueda de los generos
        JsonNode artistRoot;
        try {
            artistRoot = mapper.readTree(respuestaHTTP(URLARTIST + artistId).body());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

		//instaciar los generos
        JsonNode genres = artistRoot.get(TEXTOGENERO);
		String genre = genres.isArray() && !genres.isEmpty() ? String.join(", ", mapper.convertValue(genres, String[].class))
				: null;
		return new Cancion(songName,artistName,genre,imageUrl);
	}

}