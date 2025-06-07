package com.banduu.usuario.dto.insert;

public record UsuarioInsertDTO(
        String id,
        String nombreUsuario,
        String contrasena,
        String email
) {
}
