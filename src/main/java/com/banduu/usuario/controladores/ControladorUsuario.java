package com.banduu.usuario.controladores;


import com.banduu.usuario.dto.UsuarioDTO;
import com.banduu.usuario.servicios.ServicioUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class ControladorUsuario {
    private ServicioUsuario servicioUsuario;
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }
    @PostMapping
    public ResponseEntity<Void> insertUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        servicioUsuario.save(usuarioDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> findAllUsuarios() {
        this.servicioUsuario.findAll();
        return ResponseEntity.ok().build();
    }


}
