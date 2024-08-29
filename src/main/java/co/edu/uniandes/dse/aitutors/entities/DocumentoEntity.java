package co.edu.uniandes.dse.aitutors.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;


@Data
@Entity
public class DocumentoEntity extends BaseEntity {

    private String tipo;
    private String contenido;

    @PodamExclude
    @ManyToOne
    private TemaEntity tema;

}
