package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
    private String Tema;

    @PodamExclude
    @ManyToOne
    private InstructorEntity Instructor;

    @PodamExclude
    @ManyToOne
    private EstudianteEntity Estudiante;

    @PodamExclude
    @OneToMany(mappedBy = "Curso", cascade = CascadeType.PERSIST)
    private List<TemaEntity> temas = new ArrayList<>();
	
	
}
