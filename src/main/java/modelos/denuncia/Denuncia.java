package modelos.denuncia;

import modelos.usuario.Cliente;

import java.util.Objects;

public class Denuncia {

	private Cliente perfilDenunciado;
	private String descripcion;
	private String tipoDenuncia;

	public Denuncia() {
	}

	public Denuncia(Cliente perfilDenunciado, String descripcion, String tipoDenuncia) {
		this.perfilDenunciado = perfilDenunciado;
		this.descripcion = descripcion;
		this.tipoDenuncia = tipoDenuncia;
	}

	public Cliente getPerfilDenunciado() {
		return perfilDenunciado;
	}

	public void setPerfilDenunciado(Cliente perfilDenunciado) {
		this.perfilDenunciado = perfilDenunciado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoDenuncia() {
		return tipoDenuncia;
	}

	public void setTipoDenuncia(String tipoDenuncia) {
		this.tipoDenuncia = tipoDenuncia;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Denuncia denuncia = (Denuncia) o;
		return Objects.equals(perfilDenunciado, denuncia.perfilDenunciado) && Objects.equals(descripcion, denuncia.descripcion) && Objects.equals(tipoDenuncia, denuncia.tipoDenuncia);
	}

	@Override
	public int hashCode() {
		return Objects.hash(perfilDenunciado, descripcion, tipoDenuncia);
	}

	@Override
	public String toString() {
		return "Denuncia{" +
				"perfilDenunciado=" + perfilDenunciado +
				", descripcion='" + descripcion + '\'' +
				", tipoDenuncia='" + tipoDenuncia + '\'' +
				'}';
	}
}