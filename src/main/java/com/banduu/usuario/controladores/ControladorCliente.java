package com.banduu.usuario.controladores;

import com.banduu.usuario.dto.ClienteDTO;
import com.banduu.usuario.servicios.ServicioCliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ControladorCliente {
    private ServicioCliente servicioCliente;
    public ControladorCliente(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClienteDTO> insertCliente(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.servicioCliente.save(clienteDTO));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClienteDTO>> allClientes() {
        return ResponseEntity.ok(this.servicioCliente.findAll());
    }
}
