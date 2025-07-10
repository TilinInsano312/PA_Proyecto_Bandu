package com.banduu.usuario.controladores;


import com.banduu.usuario.dto.UsuarioDTO;
import com.banduu.usuario.servicios.ServicioUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class ControladorUsuario {
    private final ServicioUsuario servicioUsuario;
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }
    @PostMapping
    public ResponseEntity<UsuarioDTO> insertUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.servicioUsuario.save(usuarioDTO));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> findAllUsuarios() {
        this.servicioUsuario.findAll();
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findUsuarioById(@PathVariable String id) {
        UsuarioDTO usuarioDTO = this.servicioUsuario.buscarPorId(id);
        if (usuarioDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioDTO);
    }
    @PostMapping("/{id}/contrasena")
    public ResponseEntity<Void> modificarContrasena(@PathVariable String id, @RequestBody String contrasena) {
        UsuarioDTO usuarioDTO = this.servicioUsuario.buscarPorId(String.valueOf(id));
        if (usuarioDTO == null) {
            return ResponseEntity.notFound().build();
        }
        else if (contrasena == null || contrasena.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        else {
            this.servicioUsuario.modificarContrasena(id, contrasena);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/email")
    public ResponseEntity<Void> modificarEmail(@PathVariable String id, @RequestBody String email) {
        UsuarioDTO usuarioDTO = this.servicioUsuario.buscarPorId(String.valueOf(id));
        if (usuarioDTO == null) {
            return ResponseEntity.notFound().build();
        }
        else if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        else {
            this.servicioUsuario.modificarEmail(id, email);
        }
        return ResponseEntity.ok().build();
    }

}
