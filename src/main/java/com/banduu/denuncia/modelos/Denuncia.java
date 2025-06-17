package com.banduu.denuncia.modelos;

import com.banduu.usuario.modelos.Cliente;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa una denuncia en el sistema.
 * Contiene atributos como el perfil denunciado, la descripcion de la denuncia y el tipo de denuncia.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */


@Document(collection = "denuncias")
public class Denuncia {

	public Denuncia(String id, Cliente perfilDenunciado, String descripcion, String tipoDenuncia) {
		this.id = id;
		this.perfilDenunciado = perfilDenunciado;
		this.descripcion = descripcion;
		this.tipoDenuncia = tipoDenuncia;
	}

	public Denuncia() {
	}

	public String getId() {
		return id;
	}

	public Cliente getPerfilDenunciado() {
		return perfilDenunciado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getTipoDenuncia() {
		return tipoDenuncia;
	}

	@Id
	private String id;

	private Cliente perfilDenunciado;
	private String descripcion;
	private String tipoDenuncia;

}