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
	private UsuarioEntity Autor; 

	@PodamExclude
	@ManyToOne
	private AccionEntity Accion; 

	@PodamExclude
	@OneToMany(mappedBy = "Artefacto", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<ComentarioEntity> comentarios = new ArrayList<>(); 
}
