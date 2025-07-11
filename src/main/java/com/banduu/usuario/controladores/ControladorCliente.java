package com.banduu.usuario.controladores;

import com.banduu.musica.dto.AlbumDTO;
import com.banduu.musica.dto.ArtistaDTO;
import com.banduu.musica.dto.CancionDTO;
import com.banduu.usuario.dto.ClienteDTO;
import com.banduu.usuario.servicios.ServicioCliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controlador para gestionar las operaciones relacionadas con los clientes del sistema.
 * Permite insertar nuevos clientes, buscar por ID, obtener todos los clientes y modificar sus atributos.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 2.0
 */
@RestController
@RequestMapping("/api/cliente")
public class ControladorCliente {
    private final ServicioCliente servicioCliente;
    public ControladorCliente(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClienteDTO> insertCliente(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.servicioCliente.save(clienteDTO));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(this.servicioCliente.buscarPorId(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClienteDTO>> allClientes() {
        return ResponseEntity.ok(this.servicioCliente.findAll());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ClienteDTO> buscarPorIdUsuario(@PathVariable String idUsuario) {
        ClienteDTO clienteDTO = this.servicioCliente.buscarPorIdUsuario(idUsuario);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteDTO);
    }
    
    @PostMapping("/{id}/nombre")
    public ResponseEntity<Void> modificarNombre(@PathVariable String id, @RequestBody String nombre) {
        ClienteDTO clienteDTO = this.servicioCliente.buscarPorIdUsuario(id);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        } else if (nombre == null || nombre.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            this.servicioCliente.modificarNombre(id, nombre);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/apellido")
    public ResponseEntity<Void> modificarApellido(@PathVariable String id, @RequestBody String apellido) {
        ClienteDTO clienteDTO = this.servicioCliente.buscarPorIdUsuario(id);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        } else if (apellido == null || apellido.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            this.servicioCliente.modificarApellido(id, apellido);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/edad")
    public ResponseEntity<Void> modificarEdad(@PathVariable String id, @RequestBody int edad) {
        ClienteDTO clienteDTO = this.servicioCliente.buscarPorIdUsuario(id);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        } else if (edad < 0) {
            return ResponseEntity.badRequest().build();
        } else {
            this.servicioCliente.modificarEdad(id, edad);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/imagen")
    public ResponseEntity<Void> modificarImagen(@PathVariable String id, @RequestBody String imagen) {
        ClienteDTO clienteDTO = this.servicioCliente.buscarPorIdUsuario(id);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        } else if (imagen == null || imagen.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            this.servicioCliente.modificarImagen(id, imagen);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/carrera")
    public ResponseEntity<Void> modificarCarrera(@PathVariable String id, @RequestBody String carrera) {
        ClienteDTO clienteDTO = this.servicioCliente.buscarPorIdUsuario(id);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        } else if (carrera == null || carrera.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            this.servicioCliente.modificarCarrera(id, carrera);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/orientacion")
    public ResponseEntity<Void> modificarOrientacion(@PathVariable String id, @RequestBody String orientacion) {
        ClienteDTO clienteDTO = this.servicioCliente.buscarPorIdUsuario(id);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        } else if (orientacion == null || orientacion.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            this.servicioCliente.modificarOrientacion(id, orientacion);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/genero")
    public ResponseEntity<Void> modificarGenero(@PathVariable String id, @RequestBody String genero) {
        ClienteDTO clienteDTO = this.servicioCliente.buscarPorIdUsuario(id);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        } else if (genero == null || genero.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            this.servicioCliente.modificarGenero(id, genero);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/generosMusicales")
    public ResponseEntity<Void> modificarGenerosMusicales(@PathVariable String id, @RequestBody List<String> generosMusicales) {
        ClienteDTO clienteDTO = this.servicioCliente.buscarPorIdUsuario(id);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        } else if (generosMusicales == null || generosMusicales.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            this.servicioCliente.modificarGenerosMusicales(id, generosMusicales);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/canciones")
    public ResponseEntity<Void> modificarCanciones(@PathVariable String id, @RequestBody List<CancionDTO> canciones) {
        ClienteDTO clienteDTO = this.servicioCliente.buscarPorIdUsuario(id);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        } else if (canciones == null || canciones.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            this.servicioCliente.modificarCanciones(id, canciones);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/artistas")
    public ResponseEntity<Void> modificarArtistas(@PathVariable String id, @RequestBody List<ArtistaDTO> artistas) {
        ClienteDTO clienteDTO = this.servicioCliente.buscarPorIdUsuario(id);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        } else if (artistas == null || artistas.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            this.servicioCliente.modificarArtistas(id, artistas);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/albums")
public ResponseEntity<Void> modificarAlbums(@PathVariable String id, @RequestBody List<AlbumDTO> albums) {
        ClienteDTO clienteDTO = this.servicioCliente.buscarPorIdUsuario(id);
        if (clienteDTO == null) {
            return ResponseEntity.notFound().build();
        } else if (albums == null || albums.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            this.servicioCliente.modificarAlbums(id,albums);
        }
        return ResponseEntity.ok().build();
    }
}
