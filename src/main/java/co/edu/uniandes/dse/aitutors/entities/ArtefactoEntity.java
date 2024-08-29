package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa 
 *
 */

@Data
@Entity
public  class ArtefactoEntity extends BaseEntity {

	
	private String tipo;
	private String contenido;
	private UsuarioEntity autor; 

	@PodamExclude
	@ManyToOne
	private UsuarioEntity usuario; 

	@PodamExclude
	@ManyToOne
	private AccionEntity Accion; 

	@PodamExclude
	@OneToMany
	private List<ComentarioEntity> comentarios = new ArrayList<>(); 
}
