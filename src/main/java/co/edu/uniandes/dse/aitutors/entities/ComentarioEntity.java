package co.edu.uniandes.dse.aitutors.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


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
    private UsuarioEntity autor;

	@PodamExclude
    @ManyToOne 
    private UsuarioEntity usuario;
}
