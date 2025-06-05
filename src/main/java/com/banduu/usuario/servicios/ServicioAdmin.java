package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.AdminDTO;
import com.banduu.usuario.modelos.Admin;
import com.banduu.usuario.repositorios.AdminRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioAdmin {
    private AdminRepositorio adminRepositorio;
    public ServicioAdmin(AdminRepositorio adminRepositorio) {
        this.adminRepositorio = adminRepositorio;
    }
    public void save(AdminDTO admin) {
        this.adminRepositorio.insert(dtoToEntity(admin));
    }
    public List<AdminDTO> findAll() {
        this.adminRepositorio.findAll();
        return this.adminRepositorio.findAll().stream()
                .map(admin -> new AdminDTO(admin.getNombre()))
                .toList();
    }
    public Admin dtoToEntity(AdminDTO dto) {
        return new Admin(dto.nombre());
    }
}
