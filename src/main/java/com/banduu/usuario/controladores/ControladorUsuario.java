package com.banduu.usuario.controladores;

import com.banduu.usuario.dto.UsuarioDTO;
import com.banduu.usuario.servicios.ServicioUsuario;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class ControladorUsuario {
    private ServicioUsuario servicioUsuario;
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }
    @PostMapping
    public void insertUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        this.servicioUsuario.save(usuarioDTO);
    }

}
