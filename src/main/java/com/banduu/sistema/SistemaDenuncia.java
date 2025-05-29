package com.banduu.sistema;

import com.banduu.modelos.denuncia.Denuncia;
import com.banduu.modelos.usuario.Cliente;

import java.util.List;
import java.util.logging.Logger;

/**
 * Clase que representa el sistema de denuncias en la aplicacion.
 * Contiene metodos para validar denuncias y gestionar el registro de denuncias.
 *
 * @author Sebastian Sandoval
 * @version 1.0
 */

public class SistemaDenuncia {

	private static final Logger logger = Logger.getLogger(SistemaDenuncia.class.getName());

	/**
	 *
	 * El metodo revisa un objeto denuncia, revisa si es valida la denuncia y la guarda en el registro.
	 *
	 * @param denuncia El objeto denuncia a añadir en un JSON.
	 * @param esValida Indica si la denuncia es valida o no.
	 * @return void
	 */
	//Metodo para validar denuncia, al ser valida se guarda la denuncia en el registro y se borra el perfil denunciado
	public static void validarDenuncia(Denuncia denuncia, boolean esValida) {
		List<Denuncia> listaDenuncias = GestorArchivos.leerListaObjetos("Denuncia.json",Denuncia.class);
		if(esValida) {
			listaDenuncias.add(denuncia);
			GestorArchivos.borrarObjeto("PerfilUsuario.json", Cliente.class, denuncia.getPerfilDenunciado());
			GestorArchivos.escribirListaObjetos(listaDenuncias, Denuncia.class);
			logger.info("La denuncia es válida.");
		} else {
			logger.warning("La denuncia no es válida.");
		}
	}

}