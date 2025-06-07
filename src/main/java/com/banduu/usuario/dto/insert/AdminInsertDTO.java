package com.banduu.usuario.dto.insert;

public record AdminInsertDTO(
        String nombreUsuario,
        String contrasena,
        String email,
        String nombre) {
}
