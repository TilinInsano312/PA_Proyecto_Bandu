package com.banduu.usuario.mapper.impl;

import com.banduu.usuario.dto.insert.AdminInsertDTO;
import com.banduu.usuario.mapper.IConvertidorAdmin;
import com.banduu.usuario.modelos.Admin;
import org.springframework.stereotype.Component;

@Component
public class ConvertidorAdmin implements IConvertidorAdmin {
    public Admin DTOaEntidad(AdminInsertDTO dto) {
        return new Admin(dto.nombre());
    }

}
