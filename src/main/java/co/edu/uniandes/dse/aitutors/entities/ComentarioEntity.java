package co.edu.uniandes.dse.aitutors.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
