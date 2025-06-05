package com.banduu.usuario.controladores;

import com.banduu.usuario.dto.ClienteDTO;
import com.banduu.usuario.servicios.ServicioCliente;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class ControladorCliente {
    private ServicioCliente servicioCliente;
    public ControladorCliente(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCliente(@RequestBody ClienteDTO clienteDTO) {
        this.servicioCliente.save(clienteDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void allClientes() {
        this.servicioCliente.findAll();
    }
}
