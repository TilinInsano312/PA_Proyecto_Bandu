package com.banduu.denuncia.dto;

import com.banduu.usuario.modelos.Cliente;

public record DenunciaDTO(String id, Cliente perfilDenunciado, String descripcion, String tipoDenuncia) {

}
