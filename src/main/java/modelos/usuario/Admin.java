package modelos.usuario;

import java.util.Objects;

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