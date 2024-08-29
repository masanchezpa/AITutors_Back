package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;


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
	private boolean habilitacion;
	private String objetivo;

	@PodamExclude
	@OneToMany
	private List<ArtefactoEntity> artefactos = new ArrayList<>();


}
