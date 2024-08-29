package co.edu.uniandes.dse.aitutors.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import uk.co.jemos.podam.common.PodamExclude;

public class DocumentoEntity extends BaseEntity {

    private String tipo;
    private String contenido;

    @PodamExclude
    @ManyToOne
    private TemaEntity tema= new TemaEntity();

}
