package com.banduu.security.auth.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Clase que implementa UserDetails para proporcionar detalles personalizados del usuario.
 * Esta clase se utiliza para la autenticación y autorizacion en el sistema.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
public record CustomUserDetails(String id, String nombreUsuario, String contrasena, String email, String rol ) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.rol));
    }

    @Override
    public String getPassword() {
        return String.valueOf(contrasena);
    }

    @Override
    public String getUsername() {
        return String.valueOf(nombreUsuario);
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
