package com.banduu.denuncia.controlador;

import com.banduu.denuncia.dto.DenunciaDTO;
import com.banduu.denuncia.servicios.ServicioDenuncia;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/denuncia")
public class ControladorDenuncia {
    private ServicioDenuncia servicioDenuncia;
    public ControladorDenuncia(ServicioDenuncia servicioDenuncia) {
        this.servicioDenuncia = servicioDenuncia;
    }

    @PostMapping
    public ResponseEntity<DenunciaDTO> guardarDenuncia(@RequestBody DenunciaDTO denunciaDTO) {
        return ResponseEntity.ok(servicioDenuncia.save(denunciaDTO));
    }

    @GetMapping
    public ResponseEntity<List<DenunciaDTO>> listarDenuncias() {
        return ResponseEntity.ok(servicioDenuncia.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDenuncia(@RequestParam String id) {
        servicioDenuncia.delete(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/validar/{estado}")
    public ResponseEntity<Void> validarDenuncia(@RequestBody DenunciaDTO denunciaDTO, @RequestParam boolean estado) {
        servicioDenuncia.validarDenuncia(denunciaDTO, estado);
        return ResponseEntity.ok().build();
    }

}
