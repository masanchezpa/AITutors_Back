package co.edu.uniandes.dse.aitutors.entities;


import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa
 *
 */

@Data
@Entity
public  class AccionEntity extends BaseEntity {

	private String nombre;
	private String descripcion;
	private Boolean habilitacion;
	private String objetivo;

	@PodamExclude
	@OneToMany(mappedBy = "accion", cascade = CascadeType.PERSIST)
	private List<ArtefactoEntity> artefacto = new ArrayList<>();

	@PodamExclude
	@ManyToOne
	private TutorIAEntity tutorIA;


}
