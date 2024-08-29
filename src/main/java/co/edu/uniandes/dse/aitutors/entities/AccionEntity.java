package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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
	private List<AretefactoEntity> aretefactos = new ArrayList<>(); /*Aqui el tipo no es String sino Aretefacto */
	
	
}
