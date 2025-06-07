package com.banduu.usuario.controladores;

import com.banduu.usuario.servicios.ServicioUsuario;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class ControladorUsuario {
    private ServicioUsuario servicioUsuario;
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }


}
