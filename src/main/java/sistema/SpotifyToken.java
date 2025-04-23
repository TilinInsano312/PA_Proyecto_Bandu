package sistema;

import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;

public class SpotifyToken {

    private static final String client_id = "62655e57acdd4410b9dcb4c6811bf2ad";
    private static final String client_secret = "e640bc028c484c7994aaa4f886d1c171";

    public static String obtenerAccessToken() throws Exception {
        String auth = client_id + ":" + client_secret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpRequest peticion = HttpRequest.newBuilder()
                .uri(new URI("https://accounts.spotify.com/api/token"))
                .header("Authorization", "Basic " + encodedAuth)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpClient cliente = HttpClient.newHttpClient();
        HttpResponse<String> respuesta = cliente.send(peticion, BodyHandlers.ofString());

        if (respuesta.statusCode() == 200) {
            // Extraer el token del JSON
            String json = respuesta.body();
            int inicio = json.indexOf("\"access_token\":\"") + 16;
            int fin = json.indexOf("\"", inicio);
            return json.substring(inicio, fin);
        } else {
            throw new RuntimeException("Error al obtener token: " + respuesta.statusCode() + "\n" + respuesta.body());
        }
    }
}
