package sistema;

import modelos.denuncia.Denuncia;
import modelos.usuario.PerfilUsuario;

import java.util.List;
import java.util.logging.Logger;

public class SistemaDenuncia {

	private static final Logger logger = Logger.getLogger(SistemaDenuncia.class.getName());

	//Metodo para validar denuncia, al ser valida se guarda la denuncia en el registro y se borra el perfil denunciado
	public void validarDenuncia(Denuncia denuncia, boolean esValida) {
		List<Denuncia> listaDenuncias = GestorArchivos.leerListaObjetos("Denuncia.json",Denuncia.class);
		if(esValida) {
			listaDenuncias.add(denuncia);
			GestorArchivos.borrarObjeto("PerfilUsuario.json", PerfilUsuario.class, denuncia.getPerfilDenunciado());
			GestorArchivos.escribirListaObjetos(listaDenuncias, Denuncia.class);
			logger.info("La denuncia es válida.");
		} else {
			logger.warning("La denuncia no es válida.");
		}
	}

}