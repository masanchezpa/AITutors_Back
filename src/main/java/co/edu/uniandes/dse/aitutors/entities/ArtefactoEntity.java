package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

	@PodamExclude
	@OneToOne
	private UsuarioEntity autor; 

	@PodamExclude
	@ManyToOne
	private AccionEntity accion; 

	@PodamExclude
	@OneToMany(mappedBy = "artefacto", cascade = CascadeType.PERSIST)
	private List<ComentarioEntity> comentarios = new ArrayList<>(); 
}
