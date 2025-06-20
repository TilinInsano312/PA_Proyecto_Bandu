package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.AdminDTO;
import com.banduu.usuario.modelos.Admin;
import com.banduu.usuario.repositorios.AdminRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioAdmin {
    private final AdminRepositorio adminRepositorio;

    public ServicioAdmin(AdminRepositorio adminRepositorio) {
        this.adminRepositorio = adminRepositorio;
    }

    public AdminDTO save(AdminDTO admin) {
        return entidadADTO(adminRepositorio.insert(DTOaEntidad(admin)));
    }

    public List<AdminDTO> findAll() {
        adminRepositorio.findAll();
        return adminRepositorio.findAll().stream()
                .map(admin -> new AdminDTO(admin.getId(), admin.getIdUsuario(), admin.getNombre()))
                .toList();
    }
    public void delete(String id) {
        adminRepositorio.deleteById(id);
    }

    private Admin DTOaEntidad(AdminDTO dto) {
        return new Admin(dto.id(), dto.idUsuario(), dto.nombre());
    }

    private AdminDTO entidadADTO(Admin admin) {
        return new AdminDTO(admin.getId(), admin.getIdUsuario(), admin.getNombre());
    }
}
