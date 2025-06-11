package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.abreviado.AdminDTO;
import com.banduu.usuario.dto.insert.AdminInsertDTO;
import com.banduu.usuario.mapper.IConvertidorAdmin;
import com.banduu.usuario.repositorios.AdminRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioAdmin {
    private final AdminRepositorio adminRepositorio;
    private IConvertidorAdmin convertidorAdmin;
    public ServicioAdmin(AdminRepositorio adminRepositorio) {
        this.adminRepositorio = adminRepositorio;
    }
    public void save(AdminInsertDTO admin) {
        this.adminRepositorio.insert(convertidorAdmin.DTOaEntidad(admin));
    }
    public List<AdminDTO> findAll() {
        this.adminRepositorio.findAll();
        return this.adminRepositorio.findAll().stream()
                .map(admin -> new AdminDTO(admin.getId(), admin.getNombre()))
                .toList();
    }
}
