package com.banduu.sistema;

import com.banduu.spotify.servicios.SpotifyToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class SpotifyTokenTest {

    @Test
    void obtenerAccessToken() throws Exception {
        assertNotNull(SpotifyToken.obtenerAccessToken());
    }
}