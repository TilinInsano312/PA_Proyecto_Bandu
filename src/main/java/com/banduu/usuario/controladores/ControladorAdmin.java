package com.banduu.usuario.controladores;

import com.banduu.usuario.dto.AdminDTO;
import com.banduu.usuario.servicios.ServicioAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class ControladorAdmin {
    private final ServicioAdmin servicioAdmin;

    public ControladorAdmin(ServicioAdmin servicioAdmin) {
        this.servicioAdmin = servicioAdmin;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AdminDTO> insertAdmin(@RequestBody AdminDTO adminDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.servicioAdmin.save(adminDTO));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdminDTO> allAdmins() {
         return servicioAdmin.findAll();
    }
}
