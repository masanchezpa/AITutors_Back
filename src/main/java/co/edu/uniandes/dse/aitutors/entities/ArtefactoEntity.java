package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@OneToOne
	private UsuarioEntity usuario; 

	@PodamExclude
	@ManyToOne
	private AccionEntity Accion; 

	@PodamExclude
	@OneToMany
	private List<ComentarioEntity> comentarios = new ArrayList<>(); /*Aqui el tipo no es String sino Comentario */
}
