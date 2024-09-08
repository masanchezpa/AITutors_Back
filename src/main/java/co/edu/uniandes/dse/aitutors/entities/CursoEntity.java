package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PodamExclude
    @ManyToOne
    private InstructorEntity instructor;

    @PodamExclude
    @OneToMany(mappedBy = "curso") 
    private List<TemaEntity> temas = new ArrayList<>(); 
	
	
}
