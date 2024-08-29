package co.edu.uniandes.dse.aitutors.entities;


import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;


/**
 * Clase que representa 
 *
 */

@Data
@Entity
public  class CursoEntity extends BaseEntity {

	private String nombre;
	private String descripcion;
    private String Tema;
    private InstructorEntity instructor;

    @PodamExclude
    @OneToMany
    private TemaEntity temas;
	
	
}
