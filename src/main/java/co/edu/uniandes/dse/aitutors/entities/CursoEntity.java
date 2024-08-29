package co.edu.uniandes.dse.aitutors.entities;


import jakarta.persistence.Entity;

import lombok.Data;


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

	
	
}
