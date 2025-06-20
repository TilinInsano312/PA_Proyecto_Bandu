package com.banduu.denuncia.servicios;

import com.banduu.denuncia.dto.DenunciaDTO;
import com.banduu.denuncia.modelos.Denuncia;
import com.banduu.denuncia.repositorios.DenunciaRepositorio;
import com.banduu.usuario.servicios.ServicioCliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

/**
 * Clase que representa el sistema de denuncias en la aplicacion.
 * Contiene metodos para validar denuncias y gestionar el registro de denuncias.
 *
 * @author Sebastian Sandoval
 * @version 1.0
 */
@Service
public class ServicioDenuncia {

	private static final Logger logger = Logger.getLogger(ServicioDenuncia.class.getName());
	private final DenunciaRepositorio denunciaRepositorio;
	private ServicioCliente servicioCliente;
	public ServicioDenuncia(DenunciaRepositorio denunciaRepositorio) {
		this.denunciaRepositorio = denunciaRepositorio;
	}

	public DenunciaDTO save(DenunciaDTO denuncia) {
		return entidadADTO(denunciaRepositorio.insert(DTOaEntidad(denuncia)));
	}
	public List<DenunciaDTO> findAll() {
		return this.denunciaRepositorio.findAll().stream()
				.map(denuncia -> new DenunciaDTO(denuncia.getId(), denuncia.getPerfilDenunciado(), denuncia.getDescripcion(), denuncia.getTipoDenuncia()))
				.toList();
	}
	public void delete(String id) {
		denunciaRepositorio.deleteById(id);
	}


	/**
	 *
	 * El metodo revisa un objeto denuncia, revisa si es valida la denuncia y la guarda en el registro.
	 *
	 * @param denuncia El objeto denuncia a añadir en un JSON.
	 * @param esValida Indica si la denuncia es valida o no.
	 * @return void
	 */
	//Metodo para validar denuncia, al ser valida se guarda la denuncia en el registro y se borra el perfil denunciado
	public void validarDenuncia(DenunciaDTO denuncia, boolean esValida) {
		if(esValida) {
			servicioCliente.findAll()
					.stream()
					.filter(cliente -> cliente.id().equals(denuncia.perfilDenunciado().getId()))
					.findFirst()
					.ifPresent(cliente -> {
						// Eliminar el perfil denunciado
						servicioCliente.delete(cliente.id());
						logger.info("Perfil denunciado eliminado: " + cliente.id());
					});
		} else {
			logger.warning("La denuncia no es válida.");
		}
	}
	private Denuncia DTOaEntidad(DenunciaDTO dto){
		return new Denuncia(dto.id(), dto.perfilDenunciado(), dto.descripcion() ,dto.tipoDenuncia() );
	}
	private DenunciaDTO entidadADTO(Denuncia denuncia){
		return new DenunciaDTO(denuncia.getId(), denuncia.getPerfilDenunciado(), denuncia.getDescripcion(), denuncia.getTipoDenuncia());
	}

}