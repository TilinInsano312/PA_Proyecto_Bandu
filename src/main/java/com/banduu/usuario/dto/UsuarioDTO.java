package com.banduu.usuario.dto;

public record UsuarioDTO(
    String id,
    String nombreUsuario,
    String contrasena,
    String email
) {

}
