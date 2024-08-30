package co.edu.uniandes.dse.aitutors.entities;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


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
	private List<ComentarioEntity> comentarios; 
}
