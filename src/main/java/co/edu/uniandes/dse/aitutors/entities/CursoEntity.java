package co.edu.uniandes.dse.aitutors.entities;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

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
    private List<TemaEntity> temas;
	
	
}
