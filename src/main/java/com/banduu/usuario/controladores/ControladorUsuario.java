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
    @ResponseStatus(HttpStatus.CREATED)
    public void insertUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        this.servicioUsuario.save(usuarioDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> findAllUsuarios() {
        this.servicioUsuario.findAll();
        return ResponseEntity.ok().build();
    }


}
