package co.edu.uniandes.dse.aitutors.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa 
 *
 */

@Data
@Entity
public  class ComentarioEntity extends BaseEntity {


    private String contenido;
    private Date fecha;
    

	@PodamExclude
    @ManyToOne 
    private UsuarioEntity autor;
}
