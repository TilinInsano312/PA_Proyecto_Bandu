package com.banduu.security.auth.controlador;

import com.banduu.security.auth.jwt.CustomUserDetails;
import com.banduu.usuario.dto.UsuarioDTO;
import com.banduu.usuario.servicios.ServicioUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * Controlador para manejar las operaciones de auntentificacion y registro de usuarios.
 * Proporciona endpoints para iniciar sesión y registrar nuevos usuarios.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class ControladorAutenticacion {

    private final AuthenticationManager gestorAutenticacion;
    private final ServicioUsuario servicioUsuario;
    private final PasswordEncoder codificadorContrasena;

    public ControladorAutenticacion(AuthenticationManager gestorAutenticacion, 
                         ServicioUsuario servicioUsuario,
                         PasswordEncoder codificadorContrasena) {
        this.gestorAutenticacion = gestorAutenticacion;
        this.servicioUsuario = servicioUsuario;
        this.codificadorContrasena = codificadorContrasena;
    }

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody Map<String, String> datosLogin) {
        try {
            String nombreUsuario = datosLogin.get("username");
            String contrasena = datosLogin.get("password");
            
            Authentication autenticacion = gestorAutenticacion.authenticate(
                    new UsernamePasswordAuthenticationToken(nombreUsuario, contrasena)
            );

            SecurityContextHolder.getContext().setAuthentication(autenticacion);
            CustomUserDetails detallesUsuario = (CustomUserDetails) autenticacion.getPrincipal();

            return ResponseEntity.ok(Map.of(
                "exito", true,
                "mensaje", "Inicio de sesión exitoso",
                "nombreUsuario", detallesUsuario.getUsername(),
                "email", detallesUsuario.email(),
                "rol", detallesUsuario.rol()
            ));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("exito", false, "mensaje", "Credenciales inválidas"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("exito", false, "mensaje", "Error en el servidor"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody Map<String, String> datosRegistro) {
        try {
            String nombreUsuario = datosRegistro.get("username");
            String email = datosRegistro.get("email");
            String contrasena = datosRegistro.get("password");

            UsuarioDTO nuevoUsuario = new UsuarioDTO(
                null,
                nombreUsuario,
                codificadorContrasena.encode(contrasena),
                email
            );

            servicioUsuario.save(nuevoUsuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("exito", true, "mensaje", "Usuario registrado exitosamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("exito", false, "mensaje", "Error al registrar usuario: " + e.getMessage()));
        }
    }
}
