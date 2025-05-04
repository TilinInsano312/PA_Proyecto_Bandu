package modelos.usuario;

import java.util.Objects;

/**
 * Clase que representa un administrador en el sistema.
 * Hereda de la clase Usuario y contiene un atributo adicional: nombre.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */

public class Admin extends Usuario {

	private String nombre;

	public Admin(String usuario, String contrasena, String email, String nombre) {
		super(usuario, contrasena, email);
		this.nombre = nombre;
	}

	public Admin() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Admin admin = (Admin) o;
		return Objects.equals(nombre, admin.nombre);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), nombre);
	}

	@Override
	public String toString() {
		return "Admin{" +
				"nombre='" + nombre + '\'' +
				'}';
	}
}