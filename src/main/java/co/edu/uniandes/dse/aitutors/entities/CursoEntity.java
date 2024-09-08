package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;



@Data
@Entity
public  class CursoEntity extends BaseEntity {

	private String nombre;
	private String descripcion;

    @PodamExclude
    @ManyToOne
    private InstructorEntity instructor;
    
    @PodamExclude
    @ManyToOne
    private EstudianteEntity estudiante;
 
    @PodamExclude
    @OneToMany(mappedBy = "curso") 
    private List<TemaEntity> temas = new ArrayList<>(); 
	
}
