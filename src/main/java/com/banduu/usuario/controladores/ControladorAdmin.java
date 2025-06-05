package com.banduu.usuario.controladores;

import com.banduu.usuario.dto.AdminDTO;
import com.banduu.usuario.servicios.ServicioAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class ControladorAdmin {
    private ServicioAdmin servicioAdmin;
    public ControladorAdmin(ServicioAdmin servicioAdmin) {
        this.servicioAdmin = servicioAdmin;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertAdmin(@RequestBody AdminDTO adminDTO) {
        this.servicioAdmin.save(adminDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void allAdmins() {
        this.servicioAdmin.findAll();
    }
}
