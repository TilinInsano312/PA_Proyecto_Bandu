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

public class ServicioMusica {
	private static final String ACCESS_TOKEN;
    static {
        try {
            ACCESS_TOKEN = "Bearer "+SpotifyToken.obtenerAccessToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	// url
    private static final String urlBusqueda= "https://api.spotify.com/v1/search?q=";
	private static final String urlArtista= "https://api.spotify.com/v1/artists/";

	private static final String tipo = "&type=";
	private static final String parametros = "&market=ch&limit=1&offset=0&include_external=audio";

	public Album buscarAlbum(String busqueda) {
		busqueda = busqueda.replace(" ","+");
		String urlConsulta = urlBusqueda+busqueda+tipo+"album"+parametros;

		//conexion
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(urlConsulta))
				.header("Authorization", ACCESS_TOKEN)
				.GET()
				.build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
		//lectura del body
        ObjectMapper mapper = new ObjectMapper();
        JsonNode raiz = null;
        try {
			raiz = mapper.readTree(response.body());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode album = raiz.at("/albums/items/0");
		if (album.isMissingNode()) {
			System.out.println("No se encontró ningún álbum con ese offset.");
			return null;
		}

		//instaciar las variables del body
		String albumName = album.get("name").asText();
		String imageUrl = album.at("/images/0/url").asText();
		JsonNode artist = album.at("/artists/0");
		String artistName = artist.get("name").asText();
		String artistId = artist.get("id").asText();

		// Obtener género del artista
		HttpRequest artistRequest = HttpRequest.newBuilder()
				.uri(URI.create(urlArtista + artistId))
				.header("Authorization", ACCESS_TOKEN)
				.GET()
				.build();

        HttpResponse<String> artistResponse = null;
        try {
            artistResponse = client.send(artistRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        JsonNode artistRoot = null;
        try {
            artistRoot = mapper.readTree(artistResponse.body());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode genres = artistRoot.get("genres");
		String genre = genres.isArray() && genres.size() > 0 ? genres.get(0).asText() : "No disponible";

        return new Album(albumName,artistName,genre,imageUrl);
    }

	public Artista buscarArtista(String busqueda) {
		busqueda = busqueda.replace(" ","+");
		//Conexion
		String urlConsulta = urlBusqueda+busqueda+tipo+"artist"+parametros;
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(urlConsulta))
				.header("Authorization", ACCESS_TOKEN)
				.GET()
				.build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

		//lectura del body
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(response.body());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode artist = root.at("/artists/items/0");
		if (artist.isMissingNode()) {
			System.out.println("No se encontró el artista.");
			return null;
		}

		//instanciar las variables del body
		String nombreArtista = artist.get("name").asText();
		JsonNode generos = artist.get("genres");
		String genero = generos.isArray() && generos.size() > 0 ? String.join(", ", mapper.convertValue(generos, String[].class))
				: "No disponible";

		String imagenUrl = artist.has("images") && artist.get("images").size() > 0 ? artist.get("images").get(0).get("url").asText()
				: "No disponible";

		return new Artista(nombreArtista,genero,imagenUrl);
	}

	public Cancion buscarCancion(String busqueda) {
		busqueda = busqueda.replace(" ","+");
		HttpClient client = HttpClient.newHttpClient();

		//conexion
		String urlConsulta = urlBusqueda+busqueda+tipo+"track"+parametros;
		HttpRequest trackRequest = HttpRequest.newBuilder()
				.uri(URI.create(urlConsulta))
				.header("Authorization", ACCESS_TOKEN)
				.GET()
				.build();
        HttpResponse<String> trackResponse = null;
        try {
            trackResponse = client.send(trackRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

		//lectura del body
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(trackResponse.body());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode track = root.at("/tracks/items/0");
		if (track.isMissingNode()) {
			System.out.println("No se encontró la canción.");
			return null;
		}

		//instanciar variables del body
		String songName = track.get("name").asText();
		String imageUrl = track.at("/album/images/0/url").asText();
		JsonNode artistNode = track.at("/artists/0");
		String artistName = artistNode.get("name").asText();
		String artistId = artistNode.get("id").asText();

		//busqueda de los generos
		HttpRequest artistRequest = HttpRequest.newBuilder()
				.uri(URI.create("https://api.spotify.com/v1/artists/" + artistId))
				.header("Authorization", ACCESS_TOKEN)
				.GET()
				.build();
        HttpResponse<String> artistResponse = null;
		//lectura del body
        try {
            artistResponse = client.send(artistRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        JsonNode artistRoot = null;
        try {
            artistRoot = mapper.readTree(artistResponse.body());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

		//instaciar los generos
        JsonNode genres = artistRoot.get("genres");
		String genre = genres.isArray() && genres.size() > 0 ? String.join(", ", mapper.convertValue(genres, String[].class))
				: "No disponible";
		return new Cancion(songName,artistName,genre,imageUrl);
	}

}