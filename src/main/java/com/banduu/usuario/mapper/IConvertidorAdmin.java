package com.banduu.usuario.mapper;

import com.banduu.usuario.dto.insert.AdminInsertDTO;
import com.banduu.usuario.modelos.Admin;

public interface IConvertidorAdmin {
    Admin DTOaEntidad(AdminInsertDTO dto);
}
