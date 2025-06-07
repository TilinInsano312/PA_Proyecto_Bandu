package com.banduu.usuario.controladores;

import com.banduu.usuario.dto.abreviado.AdminDTO;
import com.banduu.usuario.dto.abreviado.UsuarioDTO;
import com.banduu.usuario.dto.insert.AdminInsertDTO;
import com.banduu.usuario.servicios.ServicioAdmin;
import com.banduu.usuario.servicios.ServicioUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void insertAdmin(@RequestBody AdminInsertDTO adminDTO) {
        this.servicioAdmin.save(adminDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdminDTO> allAdmins() {
         return servicioAdmin.findAll();
    }
}
