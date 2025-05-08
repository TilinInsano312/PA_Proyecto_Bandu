package modelos.usuario;

import java.util.Objects;

/**
 * Clase que representa un usuario en el sistema.
 * Contiene atributos como usuario, contrasena y email.
 *
 * @author Vicente Salazar, Sebastian Sandoval
 * @version 1.0
 */
public class Usuario {

	private String usuario;
	private String contrasena;
	private String email;

	public Usuario(String usuario, String contrasena, String email) {
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.email = email;
	}

	public Usuario() {
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Usuario{" +
				"usuario='" + usuario + '\'' +
				", contrasena='" + contrasena + '\'' +
				", email='" + email + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Usuario usuario1 = (Usuario) o;
		return Objects.equals(usuario, usuario1.usuario) && Objects.equals(contrasena, usuario1.contrasena) && Objects.equals(email, usuario1.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuario, contrasena, email);
	}
}