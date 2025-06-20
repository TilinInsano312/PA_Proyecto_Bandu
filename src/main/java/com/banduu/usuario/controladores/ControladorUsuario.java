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
    public ResponseEntity<UsuarioDTO> findUsuarioById(@PathVariable Long id) {
        UsuarioDTO usuarioDTO = this.servicioUsuario.buscarPorId(String.valueOf(id));
        if (usuarioDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioDTO);
    }

}
