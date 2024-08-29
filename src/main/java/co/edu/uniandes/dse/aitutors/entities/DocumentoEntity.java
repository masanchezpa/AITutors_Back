package co.edu.uniandes.dse.aitutors.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

public class DocumentoEntity extends BaseEntity {

    private String tipo;
    private String contenido;

    @PodamExclude
    @ManyToOne
    private TemaEntity tema;

}
