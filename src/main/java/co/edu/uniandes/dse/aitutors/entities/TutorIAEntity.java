package co.edu.uniandes.dse.aitutors.entities;


import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;

@Data
@Entity
public class TutorIAEntity extends BaseEntity{
    private String nombre;
    private String especialidad;

    @PodamExclude
    @OneToMany(
         mappedBy = "nombre",
         fetch=FetchType.LAZY)
    private List<AccionEntity> acciones=new ArrayList<>();

    @PodamExclude
    @OneToOne
    private TemaEntity tema;
}
