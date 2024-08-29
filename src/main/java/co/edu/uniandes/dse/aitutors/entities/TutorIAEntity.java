package co.edu.uniandes.dse.aitutors.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.FetchType;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.List;
import java.util.ArrayList;

@Data
@Entity
public class TutorIAEntity extends BaseEntity{
    private String nombre;
    private String especialidad;

    @PodamExclude
    @OneToMany(
         mappedBy = "accion",
         fetch=FetchType.LAZY)
    private List<AccionEntity> acciones=new ArrayList<>();
    
    @PodamExclude
    @OneToOne
    private TemaEntity tema=new TemaEntity();
}
