package com.banduu.usuario.servicios;

import com.banduu.usuario.dto.UsuarioDTO;
import com.banduu.usuario.modelos.Usuario;
import com.banduu.usuario.repositorios.UsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Servicio para gestionar las operaciones relacionadas con los usuarios.
 * Proporciona metodos para guardar, listar, buscar por ID y eliminar usuarios.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
@Service
public class ServicioUsuario {

    private UsuarioRepositorio usuarioRepositorio;
    public ServicioUsuario(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        return entidadADTO(usuarioRepositorio.insert(DTOaEntidad(usuarioDTO)));
    }
    public UsuarioDTO buscarPorId(String id) {
        return entidadADTO(this.usuarioRepositorio.findById(id).orElse(null));
    }
    public List<UsuarioDTO> findAll() {
        return this.usuarioRepositorio.findAll().stream()
                .map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getNombreUsuario(), usuario.getContrasena(), usuario.getEmail()))
                .toList();
    }
    public void delete(String id) {
        usuarioRepositorio.deleteById(id);
    }
    private Usuario DTOaEntidad(UsuarioDTO dto){
        return new Usuario(dto.id(), dto.nombreUsuario(), dto.contrasena(), dto.email());
    }
    private UsuarioDTO entidadADTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getId(), usuario.getNombreUsuario(), usuario.getContrasena(), usuario.getEmail());
    }

    public void modificarContrasena(String id, String contrasena) {
        usuarioRepositorio.updateContrasenaById(id, contrasena);
    }
    public void modificarEmail(String id, String email) {
        usuarioRepositorio.updateEmailById(id, email);
    }
}
