package co.edu.uniandes.dse.aitutors.entities;


import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
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
	@OneToMany(mappedBy = "Accion", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<ArtefactoEntity> artefacto = new ArrayList<>();


}
