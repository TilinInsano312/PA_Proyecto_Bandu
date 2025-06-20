package com.banduu.security.auth.jwt;

import com.banduu.usuario.modelos.Usuario;
import com.banduu.usuario.repositorios.AdminRepositorio;
import com.banduu.usuario.repositorios.UsuarioRepositorio;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final AdminRepositorio adminRepositorio;

    public CustomUserDetailService(UsuarioRepositorio usuarioRepositorio, AdminRepositorio adminRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.adminRepositorio = adminRepositorio;
    }

    Logger logger = Logger.getLogger(CustomUserDetailService.class.getName());
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Usuario> usuarioOptional = usuarioRepositorio.findByNombreUsuario(username);

        if (usuarioOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        Usuario usuario = usuarioOptional.get();

        return new CustomUserDetails(
                usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getContrasena(),
                usuario.getEmail(),
                adminRepositorio.findByIdUsuario(usuario.getId()).isPresent() ? "ADMIN" : "USER"
        );



    }
}
