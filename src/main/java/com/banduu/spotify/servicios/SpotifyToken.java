package com.banduu.spotify.servicios;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;

/**
 * La clase SpotifyToken se encarga de obtener un token de acceso de Spotify utilizando el flujo de credenciales del cliente.
 * Este token es necesario para realizar peticiones a la API de Spotify.
 *
 * @author Vicente Salazar
 * @version 1.0
 */
public class SpotifyToken {

    private static final String CLIENTE_ID = "62655e57acdd4410b9dcb4c6811bf2ad";
    private static final String CLIENTE_SECRETRO = "e640bc028c484c7994aaa4f886d1c171";

    /**
     * El metodo obtiene un token de acceso de Spotify utilizando el flujo de credenciales del cliente.
     * Se necesita las credenciales de la web API de spotify para realizar la peticion.
     *
     * @return El token de acceso como una cadena.
     * @throws Exception Si ocurre un error al realizar la petición HTTP ademas del codigo de error.
     */

    public static String obtenerAccessToken() {
        String auth = CLIENTE_ID + ":" + CLIENTE_SECRETRO;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpRequest peticion = HttpRequest.newBuilder()
                .uri(URI.create("https://accounts.spotify.com/api/token"))
                .header("Authorization", "Basic " + encodedAuth)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpClient cliente = HttpClient.newHttpClient();
        HttpResponse<String> respuesta;
        try {
            respuesta = cliente.send(peticion, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalArgumentException(e);
        }

        if (respuesta.statusCode() == 200) {
            // Extraer el token del JSON
            String json = respuesta.body();
            int inicio = json.indexOf("\"access_token\":\"") + 16;
            int fin = json.indexOf("\"", inicio);
            return json.substring(inicio, fin);
        } else {
            Thread.currentThread().interrupt();
            throw new IllegalArgumentException("Error al obtener token: " + respuesta.statusCode() + "\n" + respuesta.body());
        }
    }
}
