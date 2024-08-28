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
public  class AretefactoEntity extends BaseEntity {

	private String tipo;
	private String contenido;
	private String autor; /*Aqui el tipo no es String sino Usuario */

	@PodamExclude
	@OneToOne
	private String usuario; /*Aqui el tipo no es String sino Usuario */

	@PodamExclude
	@ManyToOne
	private String Accion; /*Aqui el tipo no es String sino Accion */

	@PodamExclude
	@OneToMany
	private List<String> comentarios = new ArrayList<>(); /*Aqui el tipo no es String sino Comentario */
}
